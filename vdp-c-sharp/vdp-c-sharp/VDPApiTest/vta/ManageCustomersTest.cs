using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Configuration;
using System.Collections.Generic;

namespace Vdp
{
    [TestClass]
    public class ManageCustomersTest
    {
        string createCustomerRequest;
        private VisaAPIClient visaAPIClient;

        public ManageCustomersTest()
        {
            visaAPIClient = new VisaAPIClient();
            string strDate = DateTime.UtcNow.ToString("yyyy-MM-ddThh:mm:ss.fffZ");
            createCustomerRequest = "{"
                         + "\"customer\": {"
                                         + "\"cards\": ["
                                             + "{"
                                                 + "\"address\":"+ ConfigurationManager.AppSettings["vtaCreateCustomerAddress"] + ","
                                                 + "\"billCycleDay\": \"7\","
                                                 + "\"bin\": 431263,"
                                                 + "\"cardEnrollmentDate\": \"" + strDate + "\","
                                                 + "\"cardExpiryDate\": \"" + ConfigurationManager.AppSettings["vtaCreateCustomerCardExpiryDate"] + "\","
                                                 + "\"cardNickName\": \"My Card\","
                                                 + "\"cardNumber\": \"" + ConfigurationManager.AppSettings["vtaCreateCustomerCardNumber"] + "\","
                                                 + "\"cardSecurityCode\": \"" + ConfigurationManager.AppSettings["vtaCreateCustomerCardSecurityCode"] + "\","
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
                                                 + "\"isActive\": " + ConfigurationManager.AppSettings["vtaCreateCustomerIsActive"] + ","
                                                 + "\"lastFour\": \"" + ConfigurationManager.AppSettings["vtaCreateCustomerLastFour"] + "\","
                                                 + "\"nameOnCard\": \"Migration\","
                                                 + "\"paused\": false,"
                                                 + "\"portfolioNum\": \"" + ConfigurationManager.AppSettings["vtaPortfolioNumber"] + "\","
                                                 + "\"previousCardNumber\": null,"
                                                 + "\"productId\": null,"
                                                 + "\"productIdDescription\": \"Credit\","
                                                 + "\"productType\": \"Credit\","
                                                 + "\"productTypeExtendedCode\": \"Credit\","
                                                 + "\"rpin\": null"
                                             + "}"
                                         + "],"
                                         + "\"communityCode\": \"" + ConfigurationManager.AppSettings["vtaCommunityCode"] + "\","
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
                                         + "\"isActive\": " + ConfigurationManager.AppSettings["vtaCreateCustomerIsActive"] + ","
                                         + "\"lastName\": \"Bond\","
                                         + "\"preferredCountryCode\": \"" + ConfigurationManager.AppSettings["vtaCreateCustomerPreferedCountryCode"] + "\","
                                         + "\"preferredCurrencyCode\": \"" + ConfigurationManager.AppSettings["vtaCreateCustomerPreferedCurrencyCode"] + "\","
                                         + "\"preferredFuelAmount\": \"75\","
                                         + "\"preferredLanguage\": \"" + ConfigurationManager.AppSettings["vtaCreateCustomerPreferedLanguage"] + "\","
                                         + "\"preferredTimeZone\": \"" + ConfigurationManager.AppSettings["vtaCreateCustomerPreferedTimeZone"] + "\","
                                         + "\"preferredTipPercentage\": \"15\""
                                     + "}"
                                 + "}";
        }

        [TestMethod]
        public void TestCreateCustomer()
        {
            string baseUri = "vta/";
            string resourcePath = "v3/communities/" + ConfigurationManager.AppSettings["vtaCommunityCode"] + "/customers";
            Dictionary<string, string> headers = new Dictionary<string, string>();
            headers.Add("ServiceId", ConfigurationManager.AppSettings["vtaServiceId"]);
            string status = visaAPIClient.DoMutualAuthCall(baseUri + resourcePath, "POST", "Create Customer Details", createCustomerRequest, headers);
            Assert.AreEqual(status, "Created");
        }
    }
}
