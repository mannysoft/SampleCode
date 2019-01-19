package com.visa.vdp.vta;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import com.visa.vdp.utils.VisaAPIClient;
import com.visa.vdp.utils.MethodTypes;
import com.visa.vdp.utils.Property;
import com.visa.vdp.utils.VisaProperties;

public class TestManageCustomers {

    String createCustomerRequest;
    VisaAPIClient visaAPIClient;

    @Test(groups = "vta")
    public void setUp() {
        this.visaAPIClient = new VisaAPIClient();
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        TimeZone utc = TimeZone.getTimeZone("UTC");
        sdfDate.setTimeZone(utc);
        Date now = new Date();
        String strDate = sdfDate.format(now);
        this.createCustomerRequest = "{"
                         + "\"customer\": {"
                                         + "\"cards\": ["
                                             + "{"
                                                 + "\"address\":" + VisaProperties.getProperty(Property.VTA_CREATE_CARD_ADDRESS) + ","
                                                 + "\"billCycleDay\": \"7\","
                                                 + "\"bin\": 431263,"
                                                 + "\"cardEnrollmentDate\": \""+ strDate + "\","
                                                 + "\"cardExpiryDate\": \""+ VisaProperties.getProperty(Property.VTA_CREATE_CUSTOMER_EXPR_DATE)+ "\","
                                                 + "\"cardNickName\": \"My Card\","
                                                 + "\"cardNumber\": \""+ VisaProperties.getProperty(Property.VTA_CREATE_CUSTOMER_CARD_NUM)+ "\","
                                                 + "\"cardSecurityCode\": \"" + VisaProperties.getProperty(Property.VTA_CREATE_CARD_SECURITY_CODE) + "\","
                                                 + "\"contactServiceOfferings\": ["
                                                     + "{"
                                                         + "\"contact\": {"
                                                             + "\"contactNickName\": \"testEmail\","
                                                             + "\"contactType\": \"Email\","
                                                             + "\"contactValue\": \"john@visa.com\","
                                                             + "\"isVerified\": true,"
                                                             + "\"lastUpdateDateTime\": \"" + strDate + "\","
                                                             + "\"mobileCountryCode\": null,"
                                                             + "\"mobileVerificationCode\": null,"
                                                             + "\"mobileVerificationCodeDate\": \"" + strDate + "\","
                                                             + "\"platform\": \"None\","
                                                             + "\"preferredEmailFormat\": \"Html\","
                                                             + "\"securityPhrase\": null,"
                                                             + "\"status\": \"Active\""
                                                         + "},"
                                                         + "\"serviceOfferings\": ["
                                                             + "{"
                                                                 + "\"isActive\": true,"
                                                                 + "\"offeringId\": \"Threshold\","
                                                                 + "\"offeringProperties\": ["
                                                                     + "{"
                                                                         + "\"key\": \"ThresholdAmount\","
                                                                         + "\"value\": \"10\""
                                                                     + "}"
                                                                 + "]"
                                                             + "},"
                                                             + "{"
                                                                 + "\"isActive\": true,"
                                                                 + "\"offeringId\": \"CrossBorder\","
                                                                 + "\"offeringProperties\": ["
                                                                     + "{"
                                                                         + "\"key\": \"ThresholdAmount\","
                                                                         + "\"value\": \"10\""
                                                                     + "}"
                                                                 + "]"
                                                             + "},"
                                                             + "{"
                                                                 + "\"isActive\": true,"
                                                                 + "\"offeringId\": \"Declined\","
                                                                 + "\"offeringProperties\": ["
                                                                     + "{"
                                                                         + "\"key\": \"ThresholdAmount\","
                                                                         + "\"value\": \"10\""
                                                                     + "}"
                                                                 + "]"
                                                             + "},"
                                                             + "{"
                                                                 + "\"isActive\": true,"
                                                                 + "\"offeringId\": \"CardNotPresent\","
                                                                 + "\"offeringProperties\": ["
                                                                     + "{"
                                                                         + "\"key\": \"ThresholdAmount\","
                                                                         + "\"value\": \"10\""
                                                                     + "}"
                                                                 + "]"
                                                             + "}"
                                                         + "]"
                                                     + "}"
                                                 + "],"
                                                 + "\"isActive\": true,"
                                                 + "\"lastFour\": \"" + VisaProperties.getProperty(Property.VTA_CREATE_CUSTOMER_LAST4)  + "\","
                                                 + "\"nameOnCard\": \"Migration\","
                                                 + "\"paused\": false,"
                                                 + "\"portfolioNum\": \"" + VisaProperties.getProperty(Property.VTA_PORTFOLIO_NUMER) + "\","
                                                 + "\"previousCardNumber\": null,"
                                                 + "\"productId\": null,"
                                                 + "\"productIdDescription\": \"Credit\","
                                                 + "\"productType\": \"Credit\","
                                                 + "\"productTypeExtendedCode\": \"Credit\","
                                                 + "\"rpin\": null"
                                             + "}"
                                         + "],"
                                         + "\"communityCode\": \""+ VisaProperties.getProperty(Property.VTA_COMMUNITY_CODE) +"\","
                                         + "\"contacts\": ["
                                             + "{"
                                                 + "\"contactNickName\": \"testEmail\","
                                                 + "\"contactType\": \"Email\","
                                                 + "\"contactValue\": \"john@visa.com\","
                                                 + "\"isVerified\": true,"
                                                 + "\"lastUpdateDateTime\": \"" + strDate + "\","
                                                 + "\"mobileCountryCode\": null,"
                                                 + "\"mobileVerificationCode\": null,"
                                                 + "\"mobileVerificationCodeDate\": \"" + strDate + "\","
                                                 + "\"platform\": \"None\","
                                                 + "\"preferredEmailFormat\": \"Html\","
                                                 + "\"securityPhrase\": null,"
                                                 + "\"status\": \"Active\""
                                             + "}"
                                         + "],"
                                         + "\"customerEnrollmentDate\": \"" + strDate + "\","
                                         + "\"customerId\": \"a1bb6fe1-ea64-4269-b29d-169aebd8780a\","
                                         + "\"firstName\": \"James\","
                                         + "\"isActive\": "+ VisaProperties.getProperty(Property.VTA_CREATE_CUSTOMER_ISACTIVE) +","
                                         + "\"lastName\": \"Bond\","
                                         + "\"preferredCountryCode\": \"" + VisaProperties.getProperty(Property.VTA_PREF_CNTRY_CODE) + "\","
                                         + "\"preferredCurrencyCode\": \"" + VisaProperties.getProperty(Property.VTA_PREF_CRNCY_CODE) + "\","
                                         + "\"preferredFuelAmount\": \"75\","
                                         + "\"preferredLanguage\": \"" + VisaProperties.getProperty(Property.VTA_PREF_LANG) + "\","
                                         + "\"preferredTimeZone\": \"" + VisaProperties.getProperty(Property.VTA_PREF_TIMEZONE) + "\","
                                         + "\"preferredTipPercentage\": \"15\""
                                     + "}"
                                 + "}";
     }

     @Test(groups = "vta")
     public void testGetCustomerDetails() throws Exception {
         String baseUri = "vta/";
         String resourcePath = "v3/communities/"+VisaProperties.getProperty(Property.VTA_COMMUNITY_CODE) +"/customers";

         Map<String,String> headers = new HashMap<String,String>();
         headers.put("ServiceId", VisaProperties.getProperty(Property.VTA_SERVICE_ID));
         CloseableHttpResponse response = this.visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, "Create Customer Test", this.createCustomerRequest, MethodTypes.POST, headers);
         Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_CREATED);
         response.close();
     }
}
