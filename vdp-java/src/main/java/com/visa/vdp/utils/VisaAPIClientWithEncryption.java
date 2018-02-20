package com.visa.vdp.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VisaAPIClientWithEncryption extends VisaAPIClient{

  public CloseableHttpResponse doMutualAuthRequestWithEncryption(String path, String testInfo, String body, MethodTypes methodType, Map<String, String> headers) 
      throws Exception {
    String encryptedPayload = EncryptionUtils.getEncryptedPayload(body);
    headers.put("keyId", VisaProperties.getProperty(Property.VPP_KEYID));
    CloseableHttpResponse response = super.doMutualAuthRequest(path, testInfo, encryptedPayload, methodType, headers);
    return response;
  }
  
  @Override
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
            String responseBody = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tree);
            logger.info("ResponseBody: " + responseBody);
            EncryptionUtils.getDecryptedPayload(responseBody);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
    
    EntityUtils.consume(entity);
}

  

}
