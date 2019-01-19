using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Vdp
{
    [TestClass]
    public class FundsTransferAttributesTest
    {
        private string fundsTransferInquiry;
        private VisaAPIClient visaAPIClient;

        public FundsTransferAttributesTest()
        {
            visaAPIClient = new VisaAPIClient();
            fundsTransferInquiry =
                   "{"
                      + "\"acquirerCountryCode\": \"840\","
                      + "\"acquiringBin\": \"408999\","
                      + "\"primaryAccountNumber\": \"4957030420210512\","
                      + "\"retrievalReferenceNumber\": \"330000550000\","
                      + "\"systemsTraceAuditNumber\": \"451006\""
                    + "}";
        }

        [TestMethod]
        public void TestFundsTransferEnquiry()
        {
            string baseUri = "paai/";
            string resourcePath = "fundstransferattinq/v1/cardattributes/fundstransferinquiry";
            string status = visaAPIClient.DoMutualAuthCall(baseUri + resourcePath, "POST", "Funds Transfer Attributes Inquiry Test", fundsTransferInquiry);
            Assert.AreEqual(status, "OK");
        }
    }
}
