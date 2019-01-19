package com.visa.vdp.foreignexchange;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;

import java.util.HashMap;

import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.visa.vdp.utils.VisaAPIClient;
import com.visa.vdp.utils.MethodTypes;

public class TestForeignExchangeRates {

    String foreignExchangeRequest;
    VisaAPIClient visaAPIClient;

    @BeforeTest(groups = "foreignExchange")
    public void setup() {
        this.visaAPIClient = new VisaAPIClient();
        this.foreignExchangeRequest = 
                "{"
                        + "\"acquirerCountryCode\": \"840\","
                        + "\"acquiringBin\": \"408999\","
                        + "\"cardAcceptor\": {"
                            + "\"address\": {"
                                + "\"city\": \"San Francisco\","
                                + "\"country\": \"USA\","
                                + "\"county\": \"San Mateo\","
                                + "\"state\": \"CA\","
                                + "\"zipCode\": \"94404\""
                                + "},"
                                + "\"idCode\": \"ABCD1234ABCD123\","
                                + "\"name\": \"ABCD\","
                                + "\"terminalId\": \"ABCD1234\""
                                + "},"
                                + "\"destinationCurrencyCode\": \"826\","
                                + "\"markUpRate\": \"1\","
                                + "\"retrievalReferenceNumber\": \"201010101031\","
                                + "\"sourceAmount\": \"100.00\","
                                + "\"sourceCurrencyCode\": \"840\","
                                + "\"systemsTraceAuditNumber\": \"350421\""
                        + "}";
    }

    @Test(groups = "foreignExchange")
    public void testForeignExchangeRates() throws Exception {
        String baseUri = "forexrates/";
        String resourcePath = "v1/foreignexchangerates";

        CloseableHttpResponse response = this.visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, "Foreign Exchange Test", this.foreignExchangeRequest, MethodTypes.POST, new HashMap<String, String>());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        response.close();
    }

}
