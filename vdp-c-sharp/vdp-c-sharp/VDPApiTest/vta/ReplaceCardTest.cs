using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using System.Configuration;
using System.Collections.Generic;

namespace Vdp
{
    [TestClass]
    public class ReplaceCardTest
    {
        string replaceCardsRequest;
        private VisaAPIClient visaAPIClient;

        public ReplaceCardTest()
        {
            visaAPIClient = new VisaAPIClient();
            string strDate = DateTime.UtcNow.ToString("yyyy-MM-ddThh:mm:ss.fffZ");
            replaceCardsRequest = "{"
                         + "\"communityCode\": \"" + ConfigurationManager.AppSettings["vtaCommunityCode"] + "\","
                                         + "\"newCard\": {"
                                             + "\"address\":" + ConfigurationManager.AppSettings["vtaReplaceCardNewAddress"]  + ","
                                             + "\"billCycleDay\": \"22\","
                                             + "\"bin\": null,"
                                             + "\"cardEnrollmentDate\": \"2016-06-10T08:36:59+00:00\","
                                             + "\"cardExpiryDate\": \"" + ConfigurationManager.AppSettings["vtaReplaceCardExpiryDate"] + "\","
                                             + "\"cardNickName\": \"My Visa 3\","
                                             + "\"cardNumber\": \"" + ConfigurationManager.AppSettings["vtaReplaceCardNumber"] + "\","
                                             + "\"cardSecurityCode\": \"" + ConfigurationManager.AppSettings["vtaReplaceCardSecurityCode"] + "\","
                                             + "\"isActive\": true,"
                                             + "\"lastFour\": \"" + ConfigurationManager.AppSettings["vtaReplaceCardLastFour"] + "\","
                                             + "\"nameOnCard\": \"Mradul\","
                                             + "\"paused\": false,"
                                             + "\"portfolioNum\": \"" + ConfigurationManager.AppSettings["vtaPortfolioNumber"] + "\","
                                             + "\"previousCardNumber\": null,"
                                             + "\"productId\": null,"
                                             + "\"productIdDescription\": \"Credit\","
                                             + "\"productType\": \"Credit\","
                                             + "\"productTypeExtendedCode\": \"123\","
                                             + "\"rpin\": null"
                                         + "},"
                                         + "\"oldCard\": {"
                                             + "\"address\":" + ConfigurationManager.AppSettings["vtaReplaceCardNewAddress"] + ","
                                             + "\"billCycleDay\": \"22\","
                                             + "\"bin\": null,"
                                             + "\"cardEnrollmentDate\": \"2016-06-10T08:36:59+00:00\","
                                             + "\"cardExpiryDate\": \"" + ConfigurationManager.AppSettings["vtaCreateCustomerCardExpiryDate"] + "\","
                                             + "\"cardNickName\": \"My Visa 3\","
                                             + "\"cardNumber\": \"" + ConfigurationManager.AppSettings["vtaCreateCustomerCardNumber"] + "\","
                                             + "\"cardSecurityCode\": \"" + ConfigurationManager.AppSettings["vtaCreateCustomerCardSecurityCode"] + "\","
                                         + "\"isActive\": true,"
                                         + "\"lastFour\": \"" + ConfigurationManager.AppSettings["vtaCreateCustomerLastFour"] + "\","
                                         + "\"nameOnCard\": \"ddd\","
                                         + "\"paused\": false,"
                                         + "\"portfolioNum\": \"" + ConfigurationManager.AppSettings["vtaPortfolioNumber"] + "\","
                                         + "\"previousCardNumber\": null,"
                                         + "\"productId\": null,"
                                         + "\"productIdDescription\": \"Credit\","
                                         + "\"productType\": \"Credit\","
                                         + "\"productTypeExtendedCode\": \"123\","
                                         + "\"rpin\": null"
                                       + "}"
                             + "}";
        }

        [TestMethod]
        public void TestReplaceCard()
        {
            string baseUri = "vta/";
            string resourcePath = "v3/communities/" + ConfigurationManager.AppSettings["vtaCommunityCode"] + "/cards";
            Dictionary<string, string> headers = new Dictionary<string, string>();
            headers.Add("ServiceId", ConfigurationManager.AppSettings["vtaServiceId"]);
            string status = visaAPIClient.DoMutualAuthCall(baseUri + resourcePath, "POST", "Replace a card test", replaceCardsRequest, headers);
            Assert.AreEqual(status, "Created");
        }
    }
}
