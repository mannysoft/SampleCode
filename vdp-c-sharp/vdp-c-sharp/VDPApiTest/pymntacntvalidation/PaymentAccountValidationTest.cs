using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Vdp
{
    [TestClass]
    public class PaymentAccountValidationTest
    {
        private string paymentAccountValidation;
        private VisaAPIClient visaAPIClient;

        public PaymentAccountValidationTest()
        {
            visaAPIClient = new VisaAPIClient();
            paymentAccountValidation =
                     "{"
                          + "\"acquirerCountryCode\": \"840\","
                          + "\"acquiringBin\": \"408999\","
                          + "\"addressVerificationResults\": {"
                            + "\"postalCode\": \"T4B 3G5\","
                            + "\"street\": \"801 Metro Center Blv\""
                          + "},"
                          + "\"cardAcceptor\": {"
                            + "\"address\": {"
                              + "\"city\": \"San Francisco\","
                              + "\"country\": \"USA\","
                              + "\"county\": \"CA\","
                              + "\"state\": \"CA\","
                              + "\"zipCode\": \"94404\""
                            + "},"
                            + "\"idCode\": \"111111\","
                            + "\"name\": \"rohan\","
                            + "\"terminalId\": \"123\""
                          + "},"
                          + "\"cardCvv2Value\": \"672\","
                          + "\"cardExpiryDate\": \"2018-06\","
                          + "\"primaryAccountNumber\": \"4957030000313108\","
                          + "\"retrievalReferenceNumber\": \"015221743720\","
                          + "\"systemsTraceAuditNumber\": \"743720\""
                    + "}";
        }

        [TestMethod]
        public void TestCardValidation()
        {
            string baseUri = "pav/";
            string resourcePath = "v1/cardvalidation";
            string status = visaAPIClient.DoMutualAuthCall(baseUri + resourcePath, "POST", "Card Validation Test", paymentAccountValidation);
            Assert.AreEqual(status, "OK");
        }
    }
}
