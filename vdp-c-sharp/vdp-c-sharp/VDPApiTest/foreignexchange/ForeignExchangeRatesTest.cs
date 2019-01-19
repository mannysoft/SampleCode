using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Vdp
{
    [TestClass]
    public class ForeignExchangeRatesTest
    {
        private string foreignExchangeRequest;
        private VisaAPIClient visaAPIClient;

        public ForeignExchangeRatesTest()
        {
            visaAPIClient = new VisaAPIClient();
            foreignExchangeRequest =
                "{"
                          + "\"acquirerCountryCode\": \"840\","
                          + "\"acquiringBin\": \"408999\","
                          + "\"cardAcceptor\": {"
                              + "\"address\": {"
                                  + "\"city\": \"San Francisco\","
                                  + "\"country\": \"USA\","
                                  + "\"county\": \"San Mateo\","
                                  + "\"state\": \"CA\","
                                  + "\"zipCode\": \"94404\""
                            + "},"
                            + "\"idCode\": \"ABCD1234ABCD123\","
                            + "\"name\": \"ABCD\","
                            + "\"terminalId\": \"ABCD1234\""
                          + "},"
                          + "\"destinationCurrencyCode\": \"826\","
                          + "\"markUpRate\": \"1\","
                          + "\"retrievalReferenceNumber\": \"201010101031\","
                          + "\"sourceAmount\": \"100.00\","
                          + "\"sourceCurrencyCode\": \"840\","
                          + "\"systemsTraceAuditNumber\": \"350421\""
                   + "}";
        }

        [TestMethod]
        public void TestForeignExchangeRates()
        {
            string baseUri = "forexrates/";
            string resourcePath = "v1/foreignexchangerates";
            string status = visaAPIClient.DoMutualAuthCall(baseUri + resourcePath, "POST", "Foreign Exchange Rates Test", foreignExchangeRequest);
            Assert.AreEqual(status, "OK");
        }
    }
}
