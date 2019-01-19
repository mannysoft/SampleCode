using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Vdp
{
    [TestClass]
    public class MerchantLocatorAPITest
    {
        private string locatorRequest;
        private VisaAPIClient visaAPIClient;

        public MerchantLocatorAPITest()
        {
            visaAPIClient = new VisaAPIClient();
            string strDate = DateTime.UtcNow.ToString("yyyy-MM-ddThh:mm:ss.SSS");
            locatorRequest =
            "{"
                + "\"header\": {"
                + "\"messageDateTime\": \"" + strDate + "\","
                + "\"requestMessageId\": \"VCO_GMR_001\""
                + "},"
                + "\"searchAttrList\": {"
                + "\"merchantName\": \"ALOHA CAFE\","
                + "\"merchantCountryCode\": \"840\","
                + "\"latitude\": \"34.047616\","
                + "\"longitude\": \"-118.239079\","
                + "\"distance\": \"100\","
                + "\"distanceUnit\": \"M\""
                + "},"
                + "\"responseAttrList\": ["
                + "\"GNLOCATOR\""
                + "],"
                + "\"searchOptions\": {"
                + "\"maxRecords\": \"2\","
                + "\"matchIndicators\": \"true\","
                + "\"matchScore\": \"true\""
                + "}"
                + "}";
        }

        [TestMethod]
        public void TestMerchantLocatorAPI()
        {
            string baseUri = "merchantlocator/";
            string resourcePath = "v1/locator";
            string status = visaAPIClient.DoMutualAuthCall(baseUri + resourcePath, "POST", "Merchant Locator API Test", locatorRequest);
            Assert.AreEqual(status, "OK");
        }
    }
}
