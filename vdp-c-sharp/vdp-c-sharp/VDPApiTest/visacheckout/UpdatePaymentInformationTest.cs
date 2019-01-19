using System.Configuration;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Vdp
{
    [TestClass]
    public class UpdatePaymentInformationTest
    {
        private string apikey;
        private string updatePaymentInfoRequest;
        private VisaAPIClient visaAPIClient;

        public UpdatePaymentInformationTest()
        {
            visaAPIClient = new VisaAPIClient();
            apikey = ConfigurationManager.AppSettings["apiKey"];
            updatePaymentInfoRequest = "{"
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

        [TestMethod]
        public void TestUpdatePaymentInfo()
        {
            string baseUri = "wallet-services-web/";
            string resourcePath = "payment/info/{callId}";
            resourcePath = resourcePath.Replace("{callId}", ConfigurationManager.AppSettings["checkoutCallId"]);
            string queryString = "apikey=" + apikey;
            string status = visaAPIClient.DoXPayTokenCall(baseUri, resourcePath, queryString, "PUT", "Update Payment Information Test", updatePaymentInfoRequest);
            Assert.AreEqual(status, "OK");
        }
    }
}
