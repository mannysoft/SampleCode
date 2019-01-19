package com.visa.vdp.visacheckout;

import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.visa.vdp.utils.VisaAPIClient;
import com.visa.vdp.utils.MethodTypes;
import com.visa.vdp.utils.Property;
import com.visa.vdp.utils.VisaProperties;

public class TestUpdatePaymentInformation {

    String apiKey;
    String updatePaymentInfoRequest;
    VisaAPIClient visaAPIClient;

    @BeforeTest(groups = "visacheckout")
    public void setup() {
        this.visaAPIClient = new VisaAPIClient();
        this.apiKey = VisaProperties.getProperty(Property.API_KEY);
        this.updatePaymentInfoRequest = "{"
		                 + "\"orderInfo\": {"
		                 + "\"currencyCode\": \"USD\","
		                 + "\"discount\": \"5.25\","
		                 + "\"eventType\": \"Confirm\","
		                 + "\"giftWrap\": \"10.1\","
		                 + "\"misc\": \"3.2\","
		                 + "\"orderId\": \"testorderID\","
		                 + "\"promoCode\": \"testPromoCode\","
		                 + "\"reason\": \"Order Successfully Created\","
		                 + "\"shippingHandling\": \"5.1\","
		                 + "\"subtotal\": \"80.1\","
		                 + "\"tax\": \"7.1\","
		                 + "\"total\": \"101\""
		               + "}"
		            + "}";
    }

    @Test(groups = "visacheckout")
    public void testUpdatePaymentInfo() throws Exception {
        String baseUri = "wallet-services-web/";
        String resourcePath = "payment/info/{callId}";
        resourcePath = StringUtils.replace(resourcePath, "{callId}", VisaProperties.getProperty(Property.CHECKOUT_CALL_ID));

        CloseableHttpResponse response = this.visaAPIClient.doXPayTokenRequest(baseUri, resourcePath, "apikey=" + this.apiKey, "Update Payment Information Test", this.updatePaymentInfoRequest, MethodTypes.PUT, new HashMap<String, String>());
        Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        response.close();
    }
}
