<?php

namespace Vdp;

class MerchantSearchTest extends \PHPUnit_Framework_TestCase {
	
	public function setUp() {
		$this->visaAPIClient = new VisaAPIClient;
		$strDate = date('Y-m-d\TH:i:s:z', time());
		$this->searchRequest = json_encode ([
                         "header" => [
                             "messageDateTime" => $strDate,
                             "requestMessageId" => "CDISI_GMR_001",
                             "startIndex" => "1"
                         ],
                      "searchAttrList" => [
                         "merchantName" => "ALOHA CAFE",
                         "merchantStreetAddress" => "410 E 2ND ST",
                         "merchantCity" => "LOS ANGELES",
                         "merchantState" => "CA",
                         "merchantPostalCode" => "90012",
                         "merchantCountryCode" => "840",
                         "visaStoreId" => "125861096",
                         "visaMerchantId" => "11687107",
                         "businessRegistrationId" => "196007747",
                         "acquirerCardAcceptorId" => "191642760469222",
                         "acquiringBin" => "486168"
                      ],
                      "responseAttrList" => [
                         "GNSTANDARD"
                      ],
                      "searchOptions" => [
                         "maxRecords" => "2",
                         "matchIndicators" => "true",
                         "matchScore" => "true"
                      ]
                    ]);
	}
	
	public function testMerchantSearchAPI() {
		$baseUrl = "merchantsearch/";
		$resourcePath = "v1/search";
		$statusCode = $this->visaAPIClient->doMutualAuthCall ( 'post', $baseUrl.$resourcePath, 'Merchant Search Test', $this->searchRequest );
		$this->assertEquals($statusCode, "200");
	}
}
