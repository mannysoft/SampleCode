package com.visa.vdp.mlc;

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
import com.visa.vdp.utils.Property;
import com.visa.vdp.utils.VisaProperties;

public class TestLocationUpdate {

    String locationsRequestBody;
    VisaAPIClient visaAPIClient;

    @BeforeTest(groups = "mlc")
    public void setup() {
        this.visaAPIClient = new VisaAPIClient();
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        TimeZone utc = TimeZone.getTimeZone("UTC");
        sdfDate.setTimeZone(utc);
        Date now = new Date();
        String strDate = sdfDate.format(now);
        this.locationsRequestBody = 
				"{"
					+ "\"accuracy\": \"5000\","
					+ "\"cloudNotificationKey\": \"03e3ae03-a627-4241-bad6-58f811c18e46\","
					+ "\"cloudNotificationProvider\": \"1\","
					+ "\"deviceId\": \""+ VisaProperties.getProperty(Property.MLC_DEVICE_ID)+ "\","
					+ "\"deviceLocationDateTime\": \"" + strDate + "\","
					+ "\"geoLocationCoordinate\": {"
					      + "\"latitude\": \"37.558546\","
					      + "\"longitude\": \"-122.271079\""
					+ "},"
					+ "\"header\": {"
					      + "\"messageDateTime\": \"" + strDate + "\","
					      + "\"messageId\": \""+ VisaProperties.getProperty(Property.MLC_MESSAGE_ID)+ "\""
					+ "},"
					+ "\"issuerId\": \""+ VisaProperties.getProperty(Property.MLC_ISSUER_ID)+ "\","
					+ "\"provider\": \"1\","
					+ "\"source\": \"2\""
				+ "}";
	}

    @Test(groups = "mlc")
    public void testlocations() throws Exception {
        String baseUri = "mlc/";
        String resourcePath = "locationupdate/v1/locations";

        CloseableHttpResponse response = this.visaAPIClient.doMutualAuthRequest(baseUri + resourcePath,"Locations Update Test", this.locationsRequestBody, MethodTypes.POST, new HashMap<String, String>());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        response.close();
    }

}
