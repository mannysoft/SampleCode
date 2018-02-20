package com.visa.vdp.vpp;

import java.util.HashMap;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.visa.vdp.utils.MethodTypes;
import com.visa.vdp.utils.VisaAPIClientWithEncryption;

public class TestPaymentsAuth {

  String vppPaymentsAuth;
  VisaAPIClientWithEncryption visaAPIClient;

  @BeforeTest(groups = "vpp")
  public void setup() {
    this.visaAPIClient = new VisaAPIClientWithEncryption();
    this.vppPaymentsAuth = "{ "
        + "\"acctInfo\" : { "
        + "\"primryAcctNum\" : "
        + "{ "
        + "\"pan\" : \"4131710003275053\", "
        + "\"panExpDt\" : \"2070-01\" "
        + "} "
        + "}, "
        + "\"cardAcceptr\" : "
        + "{ "
        + "\"clientId\" : \"0123456789012345678901234567893\" "
        + "}, "
        + "\"freeFormDescrptnData\" : \"Freeformdata____________________________________________________________________________________98\", "
        + "\"msgIdentfctn\" : "
        + "{ "
        + "\"correlatnId\" : \"12345671234\", "
        + "\"origId\" : \"123451234567890\" "
        + "}, "
        + "\"msgTransprtData\" : \"TransportData_________24\", "
        + "\"transctn\" : "
        + "{ "
        + "\"eComData\" : "
        + "{ "
        + "\"digitalGoods\" : \"false\", "
        + "\"eciCd\" : \"7\", "
        + "\"xid\" : \"EEC4567F90123A5678B0123EA67890D2345678FF\" "
        + "}, "
        + "\"partialAuthInd\" : \"false\", "
        + "\"posData\" : "
        + "{ "
        + "\"envrnmnt\" : \"eCom\", "
        + "\"panEntryMode\" : \"OnFile\", "
        + "\"panSrce\" : \"VCIND\" "
        + "}, "
        + "\"tranAmt\" : "
        + "{ "
        + "\"amt\" : \"123.45\", "
        + "\"numCurrCd\" : \"840\" "
        + "}, "
        + "\"tranDesc\" : \"Transactiondescription_25\" "
        + "}, "
        + "\"verfctnData\" : "
        + "{ "
        + "\"billngAddr\" : "
        + "{ "
        + "\"addrLn\" : \"PO Box 12345\", "
        + "\"postlCd\" : \"12345\" "
        + "}, "
        + "\"cvv2Data\" : "
        + "{ "
        + "\"reasnAbsent\" : \"Illegible\", "
        + "\"value\" : \"AAA\" "
        + "} "
        + "} "
        + "}";
  }

  @Test(groups = "vpp")
  public void testPaymentAuthorizations() throws Exception {
    String baseUri = "acs/";
    String resourcePath = "v1/payments/authorizations";
    CloseableHttpResponse response = this.visaAPIClient
        .doMutualAuthRequestWithEncryption(
            baseUri + resourcePath, 
            "Payments Authorization", 
            this.vppPaymentsAuth, 
            MethodTypes.POST, 
            new HashMap<String, String>());
    Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    response.close();
  }
}
