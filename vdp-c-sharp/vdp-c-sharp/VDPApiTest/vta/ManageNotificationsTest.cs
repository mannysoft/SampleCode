using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Configuration;
using System.Collections.Generic;

namespace Vdp
{
    [TestClass]
    public class ManageNotificationsTest
    {
        string notificationSubscriptionRequest;
        private VisaAPIClient visaAPIClient;

        public ManageNotificationsTest()
        {
            visaAPIClient = new VisaAPIClient();
            notificationSubscriptionRequest = "{"
                        + "\"contactType\":  \"" + ConfigurationManager.AppSettings["vtaNotificationContactType"] + "\","
                        + "\"contactValue\":  \"john@visa.com\","
                        + "\"emailFormat\": \"None\","
                        + "\"last4\":  \"" + ConfigurationManager.AppSettings["vtaCreateCustomerLastFour"] + "\","
                        + "\"phoneCountryCode\": \"en-us\","
                        + "\"platform\": \"None\","
                        + "\"preferredLanguageCode\":  \"" + ConfigurationManager.AppSettings["vtaPreferredLanguageCode"] + "\","
                        + "\"serviceOffering\":  \"WelcomeMessage\","
                        + "\"serviceOfferingSubType\":  \"WelcomeMessage\","
                        + "\"substitutions\": {}"
                     + "}";
        }

        [TestMethod]
        public void TestNotificationSubscription()
        {
            string baseUri = "vta/";
            string resourcePath = "v3/communities/" + ConfigurationManager.AppSettings["vtaCommunityCode"]
                + "/portfolios/" + ConfigurationManager.AppSettings["vtaPortfolioNumber"]
                + "/customers/" + ConfigurationManager.AppSettings["vtaCustomerId"] + "/notifications";
            Dictionary<string, string> headers = new Dictionary<string, string>();
            headers.Add("ServiceId", ConfigurationManager.AppSettings["vtaServiceId"]);
            string status = visaAPIClient.DoMutualAuthCall(baseUri + resourcePath, "POST", "Notification Subscriptions Test", notificationSubscriptionRequest, headers);
            Assert.AreEqual(status, "Created");
        }
    }
}
