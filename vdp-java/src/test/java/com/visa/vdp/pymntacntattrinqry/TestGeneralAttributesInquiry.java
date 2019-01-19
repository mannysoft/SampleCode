package com.visa.vdp.pymntacntattrinqry;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;

import org.apache.http.HttpStatus;
import com.visa.vdp.utils.VisaAPIClient;
import com.visa.vdp.utils.MethodTypes;

public class TestGeneralAttributesInquiry {

    String generalAttributeInquiry;
    VisaAPIClient visaAPIClient;

    @BeforeTest(groups = "paai")
    public void setup() {
        this.visaAPIClient = new VisaAPIClient();
        this.generalAttributeInquiry = 
                "{"
                        + "\"primaryAccountNumber\": \"4465390000029077\""
              + "}";
    }

    @Test(groups = "paai")
    public void testGeneralAttributesEnquiry() throws Exception {
        String baseUri = "paai/";
        String resourcePath = "generalattinq/v1/cardattributes/generalinquiry";

        CloseableHttpResponse response = this.visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, "General Attributes Enquiry", this.generalAttributeInquiry, MethodTypes.POST, new HashMap<String, String>());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        response.close();
    }

}
