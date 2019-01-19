using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Vdp
{
    [TestClass]
    public class GeneralAttributesInquiryTest
    {
        private string generalAttributeInquiry;
        private VisaAPIClient visaAPIClient;

        public GeneralAttributesInquiryTest()
        {
            visaAPIClient = new VisaAPIClient();
            generalAttributeInquiry =
                    "{"
                      + "\"primaryAccountNumber\": \"4856200001123821\""
                  + "}";
        }

        [TestMethod]
        public void TestGeneralAttributesEnquiry()
        {
            string baseUri = "paai/";
            string resourcePath = "generalattinq/v1/cardattributes/generalinquiry";
            string status = visaAPIClient.DoMutualAuthCall(baseUri + resourcePath, "POST", "General Attributes Inquiry Test", generalAttributeInquiry);
            Assert.AreEqual(status, "OK");
        }
    }
}
