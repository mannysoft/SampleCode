package com.visa.vdp.visadirect;

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

public class TestMVisa {

    String cashInPushPayments;
    VisaAPIClient visaAPIClient;

    @BeforeTest(groups = "visadirect")
    public void setup() {
        this.visaAPIClient = new VisaAPIClient();
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        TimeZone utc = TimeZone.getTimeZone("UTC");
        sdfDate.setTimeZone(utc);
        Date now = new Date();
        String strDate = sdfDate.format(now);
        this.cashInPushPayments = 
                        "{"
                                        + "\"acquirerCountryCode\": \"643\","
                                        + "\"acquiringBin\": \"400171\","
                                        + "\"amount\": \"124.05\","
                                        + "\"businessApplicationId\": \"CI\","
                                        + "\"cardAcceptor\": {"
                                        + "\"address\": {"
                                        + "\"city\": \"Bangalore\","
                                        + "\"country\": \"IND\""
                                        + "},"
                                        + "\"idCode\": \"ID-Code123\","
                                        + "\"name\": \"Card Accpector ABC\""
                                        + "},"
                                        + "\"localTransactionDateTime\": \""+ strDate +"\","
                                        + "\"merchantCategoryCode\": \"4829\","
                                        + "\"recipientPrimaryAccountNumber\": \"4123640062698797\","
                                        + "\"retrievalReferenceNumber\": \"430000367618\","
                                        + "\"senderAccountNumber\": \"4541237895236\","
                                        + "\"senderName\": \"Mohammed Qasim\","
                                        + "\"senderReference\": \"1234\","
                                        + "\"systemsTraceAuditNumber\": \"313042\","
                                        + "\"transactionCurrencyCode\": \"USD\","
                                        + "\"transactionIdentifier\": \"381228649430015\""
                                        + "}";
    }

    @Test(groups = "visadirect")
    public void testMVisaTransactions() throws Exception {
        String baseUri = "visadirect/";
        String resourcePath = "mvisa/v1/cashinpushpayments";

        CloseableHttpResponse response = this.visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, "M Visa Transaction Test", this.cashInPushPayments, MethodTypes.POST, new HashMap<String, String>());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        response.close();
    }

}
