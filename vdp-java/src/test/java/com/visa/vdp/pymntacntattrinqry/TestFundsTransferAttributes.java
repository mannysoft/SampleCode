package com.visa.vdp.pymntacntattrinqry;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;

import org.apache.http.HttpStatus;
import com.visa.vdp.utils.VisaAPIClient;
import com.visa.vdp.utils.MethodTypes;

public class TestFundsTransferAttributes {

    String fundsTransferInquiry;
    VisaAPIClient visaAPIClient;

    @BeforeTest(groups = "paai")
    public void setup() {
        this.visaAPIClient = new VisaAPIClient();
        this.fundsTransferInquiry = 
                "{"
                        + "\"acquirerCountryCode\": \"840\","
                        + "\"acquiringBin\": \"408999\","
                        + "\"primaryAccountNumber\": \"4957030420210512\","
                        + "\"retrievalReferenceNumber\": \"330000550000\","
                        + "\"systemsTraceAuditNumber\": \"451006\""
              + "}";
    }

    @Test(groups = "paai")
    public void testFundsTransferEnquiry() throws Exception {
        String baseUri = "paai/";
        String resourcePath = "fundstransferattinq/v1/cardattributes/fundstransferinquiry";

        CloseableHttpResponse response = this.visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, "Funds Transfer Enquiry", this.fundsTransferInquiry, MethodTypes.POST, new HashMap<String, String>());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        response.close();
    }

}
