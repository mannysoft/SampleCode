package com.visa.vdp.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class VisaAPIClient {
    
    final static Logger logger = Logger.getLogger(VisaAPIClient.class);
    
    private static CloseableHttpClient mutualAuthHttpClient;
    private static CloseableHttpClient XPayHttpClient;
    
    private CloseableHttpClient fetchXPayHttpClient() {
        XPayHttpClient = HttpClients.createDefault();
        return XPayHttpClient;
    }
    
    private CloseableHttpClient fetchMutualAuthHttpClient() throws KeyManagementException, UnrecoverableKeyException,
    NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
        if (mutualAuthHttpClient == null) {
            mutualAuthHttpClient = HttpClients.custom().setSSLSocketFactory(getSSLSocketFactory()).build();
        }
        return mutualAuthHttpClient;
    }
    
    private SSLContext loadClientCertificate() throws KeyManagementException, UnrecoverableKeyException,
    NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
        SSLContext sslcontext = SSLContexts.custom()
                        .loadKeyMaterial(new File(VisaProperties.getProperty(Property.KEYSTORE_PATH)),
                                        VisaProperties.getProperty(Property.KEYSTORE_PASSWORD).toCharArray(),
                                        VisaProperties.getProperty(Property.PRIVATE_KEY_PASSWORD).toCharArray())
                        .build();
        return sslcontext;
    }
    
    private SSLConnectionSocketFactory getSSLSocketFactory() throws KeyManagementException, UnrecoverableKeyException,
    NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException {
        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(loadClientCertificate(),
                        new String[] { "TLSv1.2" }, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        return sslSocketFactory;
    }
    
    private HttpRequest createHttpRequest(MethodTypes methodType, String url) throws Exception {
    	HttpRequest request = null;
    	switch (methodType) {
    	case GET:
    		request = new HttpGet(url);
    	    break;
    	case POST:
    		request = new HttpPost(url);
    	    break;
    	case PUT:
    		request = new HttpPut(url);
    	    break;
    	case DELETE:
    		request = new HttpDelete(url);
    	    break;
    	default:
    		logger.error("Incompatible HTTP request method " + methodType);
    	}
    	return request;
   }
    
    public CloseableHttpResponse doMutualAuthRequest(String path, String testInfo, String body, MethodTypes methodType, Map<String, String> headers) 
                    throws Exception {
       
        String url = VisaProperties.getProperty(Property.END_POINT) + path;
        logRequestBody(url, testInfo, body);
        HttpRequest request = createHttpRequest(methodType, url);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        request.setHeader(HttpHeaders.ACCEPT, "application/json");
        request.setHeader(HttpHeaders.AUTHORIZATION, BasicAuthHeaderGenerator.getBasicAuthHeader());
        request.setHeader("ex-correlation-id", getCorrelationId());
        
        if (headers != null && headers.isEmpty() == false) {
            for (Entry<String, String> header : headers.entrySet()) {
            	request.setHeader(header.getKey(), header.getValue());
            }
        }
        
        if (request instanceof HttpPost) {
		    ((HttpPost) request).setEntity(new StringEntity(body, "UTF-8"));
		} else if (request instanceof HttpPut) {
		    ((HttpPut) request).setEntity(new StringEntity(body, "UTF-8"));
		}
        
        HttpHost host = new HttpHost(VisaProperties.getProperty(Property.END_POINT));
        CloseableHttpResponse response = fetchMutualAuthHttpClient().execute((HttpUriRequest) request);
        logResponse(response);
        return response;
    }
    
    public CloseableHttpResponse doXPayTokenRequest(String baseUri, String resourcePath, String queryParams, String testInfo, String body, MethodTypes methodType, Map<String, String> headers)
                    throws Exception {
        String url = VisaProperties.getProperty(Property.END_POINT) + baseUri + resourcePath + "?" + queryParams;
        logRequestBody(url, testInfo, body);
        
        String apikey = VisaProperties.getProperty(Property.API_KEY);
        
        HttpRequest request = createHttpRequest(methodType, url);
        request.setHeader("content-type", "application/json");
        String xPayToken = XPayTokenGenerator.generateXpaytoken(resourcePath, "apikey=" + apikey, body);
        request.setHeader("x-pay-token", xPayToken);
        request.setHeader("ex-correlation-id", getCorrelationId());
        
        if (headers != null && headers.isEmpty() == false) {
            for (Entry<String, String> header : headers.entrySet()) {
            	request.setHeader(header.getKey(), header.getValue());
            }
        }
        
        if (request instanceof HttpPost) {
		    ((HttpPost) request).setEntity(new StringEntity(body, "UTF-8"));
		} else if (request instanceof HttpPut) {
		    ((HttpPut) request).setEntity(new StringEntity(body, "UTF-8"));
		}
        
        CloseableHttpResponse response = fetchXPayHttpClient().execute((HttpUriRequest) request);
        logResponse(response);
        return response;
    }
    
    protected void logResponse(CloseableHttpResponse response) throws IOException {
        Header[] h = response.getAllHeaders();
        
        // Get the response json object
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuffer result = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        
        // Print the response details
        HttpEntity entity = response.getEntity();
        logger.info("Response status : " + response.getStatusLine() + "\n");
        
        logger.info("Response Headers: \n");
        
        for (int i = 0; i < h.length; i++)
            logger.info(h[i].getName() + ":" + h[i].getValue());
        logger.info("\n Response Body:");
        
        if(!StringUtils.isEmpty(result.toString())) {
            ObjectMapper mapper = getObjectMapperInstance();
            Object tree;
            try {
                tree = mapper.readValue(result.toString(), Object.class);
                logger.info("ResponseBody: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tree));
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage());
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        
        EntityUtils.consume(entity);
    }
    
    private static void logRequestBody(String URI, String testInfo, String payload) {
        ObjectMapper mapper = getObjectMapperInstance();
        Object tree;
        logger.info("URI: " + URI);
        logger.info(testInfo);
        if(!StringUtils.isEmpty(payload)) {
            try {
                tree = mapper.readValue(payload,Object.class);
                logger.info("RequestBody: " + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tree));
            } catch (JsonProcessingException e) {
                logger.error(e.getMessage());
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
    }
    
    /**
     * Get Correlation Id for the API Call.
     * @return correlation Id  
     */
    private String getCorrelationId() {
        //Passing correlation Id header is optional while making an API call. 
        return RandomStringUtils.random(10, true, true) + "_SC";
    }
    /**
     * Get New Instance of ObjectMapper
     * @return
     */
    protected static ObjectMapper getObjectMapperInstance() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true); // format json
        return mapper;
    }
    
}
