using System.Configuration;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Vdp
{
    [TestClass]
    public class ConsumerRulesTest
    {
        private string cardRegisterData;
        private VisaAPIClient visaAPIClient;

        public ConsumerRulesTest()
        {
            visaAPIClient = new VisaAPIClient();
            cardRegisterData =
                  "{"
                     + "\"primaryAccountNumber\": \"" + ConfigurationManager.AppSettings["vctcTestPan"] + "\""
                + "}";
        }

        [TestMethod]
        public void TestRegisterACard()
        {
            string baseUri = "vctc/";
            string resourcePath = "customerrules/v1/consumertransactioncontrols";
            string status = visaAPIClient.DoMutualAuthCall(baseUri + resourcePath, "POST", "Card Registration Test", cardRegisterData);
            Assert.IsTrue((status.Equals("OK")||status.Equals("Created")));
        }
    }
}
