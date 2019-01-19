using System;
using System.Configuration;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Vdp
{
    [TestClass]
    public class LocationUpdateTest
    {
        private string locationsRequestBody;
        private VisaAPIClient visaAPIClient;

        public LocationUpdateTest()
        {
            visaAPIClient = new VisaAPIClient();
            string strDate = DateTime.UtcNow.ToString("yyyy-MM-ddThh:mm:ssZ");
            locationsRequestBody =
               "{"
                    + "\"accuracy\": \"5000\","
                    + "\"cloudNotificationKey\": \"03e3ae03-a627-4241-bad6-58f811c18e46\","
                    + "\"cloudNotificationProvider\": \"1\","
                    + "\"deviceId\": \"" + ConfigurationManager.AppSettings["mlcDeviceId"] + "\","
                    + "\"deviceLocationDateTime\": \"" + strDate + "\","
                    + "\"geoLocationCoordinate\": {"
                          + "\"latitude\": \"37.558546\","
                          + "\"longitude\": \"-122.271079\""
                    + "},"
                    + "\"header\": {"
                          + "\"messageDateTime\": \"" + strDate + "\","
                          + "\"messageId\": \"" + ConfigurationManager.AppSettings["mlcMessageId"] + "\""
                    + "},"
                    + "\"issuerId\": \"" + ConfigurationManager.AppSettings["mlcIssuerId"] + "\","
                    + "\"provider\": \"1\","
                    + "\"source\": \"2\""
                + "}";
        }

        [TestMethod]
        public void TestLocationsUpdate()
        {
            string baseUri = "mlc/";
            string resourcePath = "locationupdate/v1/locations";
            string status = visaAPIClient.DoMutualAuthCall(baseUri + resourcePath, "POST", "Location Updates Test", locationsRequestBody);
            Assert.AreEqual(status, "OK");
        }
    }
}
