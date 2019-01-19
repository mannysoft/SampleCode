package com.visa.vdp.merchantsearch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.visa.vdp.utils.VisaAPIClient;
import com.visa.vdp.utils.MethodTypes;

public class TestMerchantSearchAPI {

    String searchRequest;
    VisaAPIClient visaAPIClient;

    @BeforeTest(groups = "merchantsearch")
    public void setup() {
        this.visaAPIClient = new VisaAPIClient();
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        TimeZone utc = TimeZone.getTimeZone("UTC");
        sdfDate.setTimeZone(utc);
        Date now = new Date();
        String strDate = sdfDate.format(now);

        this.searchRequest = "{"
                + "\"header\": {"
                + "\"messageDateTime\": \"" + strDate + "\","
                + "\"requestMessageId\": \"CDISI_GMR_001\","
                + "\"startIndex\": \"1\""
                + "},"
                + "\"searchAttrList\": {"
                + "\"merchantName\": \"ALOHA CAFE\","
                + "\"merchantStreetAddress\": \"410 E 2ND ST\","
                + "\"merchantCity\": \"LOS ANGELES\","
                + "\"merchantState\": \"CA\","
                + "\"merchantPostalCode\": \"90012\","
                + "\"merchantCountryCode\": \"840\","
                + "\"visaMerchantId\": \"11687107\","
                + "\"visaStoreId\": \"125861096\","
                + "\"businessRegistrationId\": \"196007747\","
                + "\"acquirerCardAcceptorId\": \"191642760469222\","
                + "\"acquiringBin\": \"486168\""
                + "},"
                + "\"responseAttrList\": ["
                + "\"GNSTANDARD\""
                + "],"
                + "\"searchOptions\": {"
                + "\"maxRecords\": \"2\","
                + "\"matchIndicators\": \"true\","
                + "\"matchScore\": \"true\""
                + "}"
                + "}";
    }

    @Test(groups = "merchantsearch")
    public void testMerchantSearch() throws Exception {
        String baseUri = "merchantsearch/";
        String resourcePath = "v1/search";

        CloseableHttpResponse response = this.visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, "Merchant Search API Test", this.searchRequest, MethodTypes.POST, new HashMap<String, String>());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        response.close();
    }

}
