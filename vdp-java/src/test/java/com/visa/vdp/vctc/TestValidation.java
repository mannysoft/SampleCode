package com.visa.vdp.vctc;

import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import com.visa.vdp.utils.VisaAPIClient;
import com.visa.vdp.utils.MethodTypes;

public class TestValidation {

    VisaAPIClient visaAPIClient;

    @Test(groups = "vctc")
    public void setUp() {
        this.visaAPIClient = new VisaAPIClient();
    }

    @Test(groups = "vctc")
    public void testRetreiveListofDecisionRecords() throws Exception {
        String baseUri = "vctc/";
        String resourcePath = "validation/v1/decisions/history";
        String queryParams = "?limit=1&page=1";

        CloseableHttpResponse response = this.visaAPIClient.doMutualAuthRequest(baseUri + resourcePath + queryParams, "Retrieve List of Recent Decision Records", "", MethodTypes.GET, new HashMap<String, String>());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        response.close();
    }
}
