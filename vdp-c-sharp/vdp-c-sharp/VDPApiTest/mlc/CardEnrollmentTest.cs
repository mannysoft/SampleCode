using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Configuration;

namespace Vdp
{
    [TestClass]
    public class CardholderEnrollmentTest
    {
        private string enrollementData;
        private VisaAPIClient visaAPIClient;

        public CardholderEnrollmentTest()
        {
            visaAPIClient = new VisaAPIClient();
            enrollementData =
            "{"
             + "\"enrollmentMessageType\": \"enroll\","
             + "\"enrollmentRequest\": {"
             + "\"cardholderMobileNumber\": \"0016504323000\","
             + "\"clientMessageID\": \"" + ConfigurationManager.AppSettings["mlcClientMessageID"] + "\","
             + "\"deviceId\": \"" + ConfigurationManager.AppSettings["mlcDeviceId"] + "\","
             + "\"issuerId\": \"" + ConfigurationManager.AppSettings["mlcIssuerId"] + "\","
             + "\"primaryAccountNumber\": \"" + ConfigurationManager.AppSettings["mlcPrimaryAccountNumber"] + "\""
             + "}"
             + "}";
        }

        [TestMethod]
        public void TestCardEnrollment()
        {
            string baseUri = "mlc/";
            string resourcePath = "enrollment/v1/enrollments";
            string status = visaAPIClient.DoMutualAuthCall(baseUri + resourcePath, "POST", "MLC Card Enrollement Test", enrollementData);
            Assert.AreEqual(status, "OK");
        }
    }
}
