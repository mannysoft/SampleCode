using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Vdp
{
    [TestClass]
    public class ValidationTest
    {
        private VisaAPIClient visaAPIClient;

        public ValidationTest()
        {
            visaAPIClient = new VisaAPIClient();
        }

        [TestMethod]
        public void TestRetreiveListofDecisionRecords()
        {
            string baseUri = "vctc/";
            string resourcePath = "validation/v1/decisions/history";
            string queryString = "?limit=1&page=1";
            string status = visaAPIClient.DoMutualAuthCall(baseUri + resourcePath + queryString, "GET", "Retreive List of Decision Records Test", "");
            Assert.AreEqual(status, "OK");
        }
    }
}
