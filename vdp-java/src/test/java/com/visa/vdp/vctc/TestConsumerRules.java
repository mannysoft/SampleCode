package com.visa.vdp.vctc;

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

public class TestConsumerRules {

    String cardRegisterData;
    VisaAPIClient visaAPIClient;

    @BeforeTest(groups = "vctc")
    public void setup() {
        this.visaAPIClient = new VisaAPIClient();
        this.cardRegisterData = 
                "{"
                        + "\"primaryAccountNumber\": \""+ VisaProperties.getProperty(Property.VCTC_TEST_PAN) +"\""
              + "}";
    }

    @Test(groups = "vctc")
    public void testRegisterACard() throws Exception {
        String baseUri = "vctc/";
        String resourcePath = "customerrules/v1/consumertransactioncontrols";

        CloseableHttpResponse response = this.visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, "Register A Card", this.cardRegisterData, MethodTypes.POST, new HashMap<String, String>());
        Assert.assertTrue((response.getStatusLine().getStatusCode() == HttpStatus.SC_OK || response.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED));
        response.close();
    }

}
