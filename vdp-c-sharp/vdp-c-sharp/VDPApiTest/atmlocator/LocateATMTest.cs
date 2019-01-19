using System;
using System.Net;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace Vdp
{
    [TestClass]
    public class LocateATMTest
    {
        private string atmInquiryRequest;
        private VisaAPIClient visaAPIClient;

        public LocateATMTest()
        {
            string strDate = DateTime.UtcNow.ToString("yyyy-MM-ddThh:mm:ssZ");
            visaAPIClient = new VisaAPIClient();
            atmInquiryRequest =
            "{"
              + "\"requestData\": {"
              + "\"culture\": \"en-US\","
              + "\"distance\": \"20\","
              + "\"distanceUnit\": \"mi\","
              + "\"location\": {"
              + "\"address\": null,"
              + "\"geocodes\": null,"
              + "\"placeName\": \"800 metro center , foster city,ca\""
              + "},"
              + "\"metaDataOptions\": 0,"
              + "\"options\": {"
              + "\"findFilters\": ["
              + "{"
              + "\"filterName\": \"PLACE_NAME\","
              + "\"filterValue\": \"FORT FINANCIAL CREDIT UNION|ULTRON INC|U.S. BANK\""
              + "},"
              + "{"
              + "\"filterName\": \"OPER_HRS\","
              + "\"filterValue\": \"C\""
              + "},"
              + "{"
              + "\"filterName\": \"AIRPORT_CD\","
              + "\"filterValue\": \"\""
              + "},"
              + "{"
              + "\"filterName\": \"WHEELCHAIR\","
              + "\"filterValue\": \"N\""
              + "},"
              + "{"
              + "\"filterName\": \"BRAILLE_AUDIO\","
              + "\"filterValue\": \"N\""
              + "},"
              + "{"
              + "\"filterName\": \"BALANCE_INQUIRY\","
              + "\"filterValue\": \"N\""
              + "},"
              + "{"
              + "\"filterName\": \"CHIP_CAPABLE\","
              + "\"filterValue\": \"N\""
              + "},"
              + "{"
              + "\"filterName\": \"PIN_CHANGE\","
              + "\"filterValue\": \"N\""
              + "},"
              + "{"
              + "\"filterName\": \"RESTRICTED\","
              + "\"filterValue\": \"N\""
              + "},"
              + "{"
              + "\"filterName\": \"PLUS_ALLIANCE_NO_SURCHARGE_FEE\","
              + "\"filterValue\": \"N\""
              + "},"
              + "{"
              + "\"filterName\": \"ACCEPTS_PLUS_SHARED_DEPOSIT\","
              + "\"filterValue\": \"N\""
              + "},"
              + "{"
              + "\"filterName\": \"V_PAY_CAPABLE\","
              + "\"filterValue\": \"N\""
              + "},"
              + "{"
              + "\"filterName\": \"READY_LINK\","
              + "\"filterValue\": \"N\""
              + "}"
              + "],"
              + "\"operationName\": \"or\","
              + "\"range\": {"
              + "\"count\": 99,"
              + "\"start\": 0"
              + "},"
              + "\"sort\": {"
              + "\"direction\": \"desc\","
              + "\"primary\": \"city\""
              + "},"
              + "\"useFirstAmbiguous\": true"
              + "}"
              + "},"
              + "\"wsRequestHeaderV2\": {"
              + "\"applicationId\": \"VATMLOC\","
              + "\"correlationId\": \"909420141104053819418\","
              + "\"requestMessageId\": \"test12345678\","
              + "\"requestTs\":\"" + strDate + "\","
              + "\"userBid\": \"10000108\","
              + "\"userId\": \"CDISIUserID\""
              + "}"
              + "}";
        }

        [TestMethod]
        public void TestLocateATM()
        {
            string baseUri = "globalatmlocator/";
            string resourcePath = "v1/localatms/atmsinquiry";
            string status = visaAPIClient.DoMutualAuthCall(baseUri + resourcePath, "POST", "Locate ATM Test", atmInquiryRequest);
            Assert.AreEqual(status, "OK");
        }
    }
}
