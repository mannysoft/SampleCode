package com.visa.vdp.pymntacntvalidation;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;

import org.apache.http.HttpStatus;
import com.visa.vdp.utils.VisaAPIClient;
import com.visa.vdp.utils.MethodTypes;

public class TestPaymentAccountValidation {

    String paymentAccountValidation;
    VisaAPIClient visaAPIClient;

    @BeforeTest(groups = "pav")
    public void setup() {
        this.visaAPIClient = new VisaAPIClient();
        this.paymentAccountValidation = 
                "{"
                        + "\"acquirerCountryCode\": \"840\","
                        + "\"acquiringBin\": \"408999\","
                        + "\"addressVerificationResults\": {"
                            + "\"postalCode\": \"T4B 3G5\","
                            + "\"street\": \"801 Metro Center Blv\""
                        + "},"
                        + "\"cardAcceptor\": {"
                            + "\"address\": {"
                                + "\"city\": \"San Francisco\","
                                + "\"country\": \"USA\","
                                + "\"county\": \"CA\","
                                + "\"state\": \"CA\","
                                + "\"zipCode\": \"94404\""
                            + "},"
                            + "\"idCode\": \"111111\","
                            + "\"name\": \"rohan\","
                            + "\"terminalId\": \"123\""
                        + "},"
                        + "\"cardCvv2Value\": \"672\","
                        + "\"cardExpiryDate\": \"2018-06\","
                        + "\"primaryAccountNumber\": \"4957030000313108\","
                        + "\"retrievalReferenceNumber\": \"015221743720\","
                        + "\"systemsTraceAuditNumber\": \"743720\""
              + "}";
    }

    @Test(groups = "pav")
    public void testCardValidation() throws Exception {
        String baseUri = "pav/";
        String resourcePath = "v1/cardvalidation";

        CloseableHttpResponse response = this.visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, "Card Validation Test", this.paymentAccountValidation, MethodTypes.POST, new HashMap<String, String>());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        response.close();
    }

}
