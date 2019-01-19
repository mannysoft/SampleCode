package com.visa.vdp.cybersource;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;

import java.util.HashMap;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.visa.vdp.utils.VisaAPIClient;
import com.visa.vdp.utils.MethodTypes;
import com.visa.vdp.utils.Property;
import com.visa.vdp.utils.VisaProperties;

public class TestCybersourcePayments {

    String apiKey;
    String paymentAuthorizationRequest;
    VisaAPIClient visaAPIClient;

    @BeforeTest(groups = "cybersource")
    public void setup() {
        this.visaAPIClient = new VisaAPIClient();
        this.apiKey = VisaProperties.getProperty(Property.API_KEY);
        this.paymentAuthorizationRequest = 
                "{\"amount\": \"0\","
                 + "\"currency\": \"USD\","
                 + "\"payment\": {"
                     + "\"cardNumber\": \"4111111111111111\","
                     + "\"cardExpirationMonth\": \"10\","
                     + "\"cardExpirationYear\": \"2020\""
                     + "}"
                 + "}";
    }

    @Test(groups = "cybersource")
    public void testPaymentAuthorizations() throws Exception {
        String baseUri = "cybersource/";
        String resourcePath = "payments/v1/authorizations";
        String queryString = "apikey=" + apiKey;

        CloseableHttpResponse response = this.visaAPIClient.doXPayTokenRequest(baseUri, resourcePath, queryString, "Payment Authorization Test", this.paymentAuthorizationRequest, MethodTypes.POST, new HashMap<String, String>());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_CREATED);
        response.close();
    }

}
