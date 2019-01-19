package com.visa.vdp.mlc;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;

import org.apache.http.HttpStatus;
import com.visa.vdp.utils.VisaAPIClient;
import com.visa.vdp.utils.MethodTypes;
import com.visa.vdp.utils.Property;
import com.visa.vdp.utils.VisaProperties;

public class TestCardholderEnrollment {

    String enrollementData;
    VisaAPIClient visaAPIClient;

    @BeforeTest(groups = "mlc")
    public void setup() {
        this.visaAPIClient = new VisaAPIClient();
        this.enrollementData = 
                "{"
                        + "\"enrollmentMessageType\": \"enroll\","
                        + "\"enrollmentRequest\": {"
                            + "\"cardholderMobileNumber\": \"0016504323000\","
                            + "\"clientMessageID\": \""+ VisaProperties.getProperty(Property.MLC_CLIENT_MESSAGE_ID)+ "\","
                            + "\"deviceId\": \""+ VisaProperties.getProperty(Property.MLC_DEVICE_ID)+ "\","
                            + "\"issuerId\": \""+ VisaProperties.getProperty(Property.MLC_ISSUER_ID)+ "\","
                            + "\"primaryAccountNumber\": \""+ VisaProperties.getProperty(Property.MLC_PRIMARY_ACCOUNT_NUMBER)+ "\""
                        + "}"
              + "}";
    }

    @Test(groups = "mlc")
    public void testCardEnrollment() throws Exception {
        String baseUri = "mlc/";
        String resourcePath = "enrollment/v1/enrollments";

        CloseableHttpResponse response = this.visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, "Card Enrollment Test", this.enrollementData, MethodTypes.POST, new HashMap<String, String>());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        response.close();
    }

}
