<?php

namespace Vdp;

class MerchantLocatorTest extends \PHPUnit_Framework_TestCase {
	
	public function setUp() {
		$this->visaAPIClient = new VisaAPIClient;
		$strDate = date('Y-m-d\TH:i:s:z', time());
		$this->locatorRequest = json_encode ([
                "header" =>  [
                    "messageDateTime" =>  $strDate,
                    "requestMessageId" =>  "VCO_GMR_001"
                ],
                "searchAttrList" =>  [
                    "merchantName" =>  "ALOHA CAFE",
                    "merchantCountryCode" =>  "840",
                    "latitude" =>  "34.047616",
                    "longitude" =>  "-118.239079",
                    "distance" =>  "100",
                    "distanceUnit" =>  "M"
                ],
                "responseAttrList" =>  [
                "GNLOCATOR"
                ],
                "searchOptions" =>  [
                    "maxRecords" =>  "2",
                    "matchIndicators" =>  "true",
                    "matchScore" =>  "true"
                ]
            ]);
	}
	
	public function testMerchantLocatorAPI() {
		$baseUrl = "merchantlocator/";
		$resourcePath = "v1/locator";
		$statusCode = $this->visaAPIClient->doMutualAuthCall ( 'post', $baseUrl.$resourcePath, 'Merchant Locator Test', $this->locatorRequest );
		$this->assertEquals($statusCode, "200");
	}
}
