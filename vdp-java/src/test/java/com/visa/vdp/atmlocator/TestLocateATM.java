package com.visa.vdp.atmlocator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.visa.vdp.utils.VisaAPIClient;
import com.visa.vdp.utils.MethodTypes;

public class TestLocateATM {

    String atmInquiryRequest;
    VisaAPIClient visaAPIClient;

    @BeforeTest(groups = "atmlocator")
    public void setup() {
        this.visaAPIClient = new VisaAPIClient();
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        this.atmInquiryRequest =
                "{"
                        + "\"requestData\": {"
                        + "\"culture\": \"en-US\","
                        + "\"distance\": \"20\","
                        + "\"distanceUnit\": \"mi\","
                        + "\"location\": {"
                        + "\"address\": null,"
                        + "\"geocodes\": null,"
                        + "\"placeName\": \"800 metro center , foster city,ca\""
                        + "},"
                        + "\"metaDataOptions\": 0,"
                        + "\"options\": {"
                        + "\"findFilters\": ["
                        + "{"
                        + "\"filterName\": \"PLACE_NAME\","
                        + "\"filterValue\": \"FORT FINANCIAL CREDIT UNION|ULTRON INC|U.S. BANK\""
                        + "},"
                        + "{"
                        + "\"filterName\": \"OPER_HRS\","
                        + "\"filterValue\": \"C\""
                        + "},"
                        + "{"
                        + "\"filterName\": \"AIRPORT_CD\","
                        + "\"filterValue\": \"\""
                        + "},"
                        + "{"
                        + "\"filterName\": \"WHEELCHAIR\","
                        + "\"filterValue\": \"N\""
                        + "},"
                        + "{"
                        + "\"filterName\": \"BRAILLE_AUDIO\","
                        + "\"filterValue\": \"N\""
                        + "},"
                        + "{"
                        + "\"filterName\": \"BALANCE_INQUIRY\","
                        + "\"filterValue\": \"N\""
                        + "},"
                        + "{"
                        + "\"filterName\": \"CHIP_CAPABLE\","
                        + "\"filterValue\": \"N\""
                        + "},"
                        + "{"
                        + "\"filterName\": \"PIN_CHANGE\","
                        + "\"filterValue\": \"N\""
                        + "},"
                        + "{"
                        + "\"filterName\": \"RESTRICTED\","
                        + "\"filterValue\": \"N\""
                        + "},"
                        + "{"
                        + "\"filterName\": \"PLUS_ALLIANCE_NO_SURCHARGE_FEE\","
                        + "\"filterValue\": \"N\""
                        + "},"
                        + "{"
                        + "\"filterName\": \"ACCEPTS_PLUS_SHARED_DEPOSIT\","
                        + "\"filterValue\": \"N\""
                        + "},"
                        + "{"
                        + "\"filterName\": \"V_PAY_CAPABLE\","
                        + "\"filterValue\": \"N\""
                        + "},"
                        + "{"
                        + "\"filterName\": \"READY_LINK\","
                        + "\"filterValue\": \"N\""
                        + "}"
                        + "],"
                        + "\"operationName\": \"or\","
                        + "\"range\": {"
                        + "\"count\": 99,"
                        + "\"start\": 0"
                        + "},"
                        + "\"sort\": {"
                        + "\"direction\": \"desc\","
                        + "\"primary\": \"city\""
                        + "},"
                        + "\"useFirstAmbiguous\": true"
                        + "}"
                        + "},"
                        + "\"wsRequestHeaderV2\": {"
                        + "\"applicationId\": \"VATMLOC\","
                        + "\"correlationId\": \"909420141104053819418\","
                        + "\"requestMessageId\": \"test12345678\","
                        + "\"requestTs\":\""+ strDate + "\","
                        + "\"userBid\": \"10000108\","
                        + "\"userId\": \"CDISIUserID\""
                        + "}"
                        + "}";
    }

    @Test(groups = "atmlocator")
    public void testLocateATM() throws Exception {
        String baseUri = "globalatmlocator/";
        String resourcePath = "v1/localatms/atmsinquiry";
        CloseableHttpResponse response = this.visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, "Locate ATM Test", this.atmInquiryRequest, MethodTypes.POST, new HashMap<String, String>());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        response.close();
    }
}
