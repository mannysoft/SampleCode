using System.Configuration;
using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Vdp
{
    [TestClass]
    public class MVisaTest
    {
        private string cashInPushPayments;
        private VisaAPIClient visaAPIClient;

        public MVisaTest()
        {
            visaAPIClient = new VisaAPIClient();
            string strDate = DateTime.UtcNow.ToString("yyyy-MM-ddThh:mm:ss");
            cashInPushPayments =
                 "{"
                 + "\"acquirerCountryCode\": \"643\","
                 + "\"acquiringBin\": \"400171\","
                 + "\"amount\": \"124.05\","
                 + "\"businessApplicationId\": \"CI\","
                 + "\"cardAcceptor\": {"
                                         + "\"address\": {"
                                                             + "\"city\": \"Bangalore\","
                                                             + "\"country\": \"IND\""
                                                      + "},"
                                         + "\"idCode\": \"ID-Code123\","
                                         + "\"name\": \"Card Accpector ABC\""
                                       + "},"
                 + "\"localTransactionDateTime\": \""+ strDate + "\","
                 + "\"merchantCategoryCode\": \"4829\","
                 + "\"recipientPrimaryAccountNumber\": \"4123640062698797\","
                 + "\"retrievalReferenceNumber\": \"430000367618\","
                 + "\"senderAccountNumber\": \"4541237895236\","
                 + "\"senderName\": \"Mohammed Qasim\","
                 + "\"senderReference\": \"1234\","
                 + "\"systemsTraceAuditNumber\": \"313042\","
                 + "\"transactionCurrencyCode\": \"USD\","
                 + "\"transactionIdentifier\": \"381228649430015\""
                 + "}";
        }

        [TestMethod]
        public void TestMVisaTransactions()
        {
            string baseUri = "visadirect/";
            string resourcePath = "mvisa/v1/cashinpushpayments";
            string status = visaAPIClient.DoMutualAuthCall(baseUri + resourcePath, "POST", "M Visa Transaction Test", cashInPushPayments);
            Assert.AreEqual(status, "OK");
        }
    }
}
