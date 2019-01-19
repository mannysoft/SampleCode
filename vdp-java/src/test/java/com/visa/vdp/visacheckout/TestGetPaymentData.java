package com.visa.vdp.visacheckout;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.visa.vdp.utils.VisaAPIClient;
import com.visa.vdp.utils.MethodTypes;
import com.visa.vdp.utils.Property;
import com.visa.vdp.utils.VisaProperties;

public class TestGetPaymentData {

    String apiKey;
    VisaAPIClient visaAPIClient;

    @BeforeTest(groups = "visacheckout")
    public void setup() {
        this.visaAPIClient = new VisaAPIClient();
        apiKey = VisaProperties.getProperty(Property.API_KEY);
    }

    @Test(groups = "visacheckout")
    public void testGetPaymentInfo() throws Exception {
        String baseUri = "wallet-services-web/";
        String resourcePath = "payment/data/{callId}";
        resourcePath = StringUtils.replace(resourcePath, "{callId}", VisaProperties.getProperty(Property.CHECKOUT_CALL_ID));

        CloseableHttpResponse response = this.visaAPIClient.doXPayTokenRequest(baseUri, resourcePath, "apikey=" + this.apiKey, "Get Payment Information Test", "", MethodTypes.GET, new HashMap<String, String>());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        response.close();
    }
}
