package com.visa.vdp.vta;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import com.visa.vdp.utils.VisaAPIClient;
import com.visa.vdp.utils.MethodTypes;
import com.visa.vdp.utils.Property;
import com.visa.vdp.utils.VisaProperties;

public class TestManagePortfolios {

    VisaAPIClient visaAPIClient;

    @Test(groups = "vta")
    public void setUp() {
        this.visaAPIClient = new VisaAPIClient();
    }

    @Test(groups = "vta")
    public void testPortfolios() throws Exception {
        String baseUri = "vta/";
        String resourcePath = "v3/communities/"+VisaProperties.getProperty(Property.VTA_COMMUNITY_CODE) +"/portfolios";

        Map<String,String> headers = new HashMap<String,String>();
        headers.put("ServiceId", VisaProperties.getProperty(Property.VTA_SERVICE_ID));
        CloseableHttpResponse response = this.visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, "Get Portfolio Details Test", "", MethodTypes.GET, headers);
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        response.close();
    }
}
