using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Vdp
{
    [TestClass]
    public class ProgramAdministrationTest
    {
        private VisaAPIClient visaAPIClient;

        public ProgramAdministrationTest()
        {
            visaAPIClient = new VisaAPIClient();
        }

        [TestMethod]
        public void TestRetreiveTransactionTypeControls()
        {
            string baseUri = "vctc/";
            string resourcePath = "programadmin/v1/configuration/transactiontypecontrols";
            string status = visaAPIClient.DoMutualAuthCall(baseUri + resourcePath, "GET", "Retreive Transaction Type Controls Test", "");
            Assert.AreEqual(status, "OK");
        }
    }
}
