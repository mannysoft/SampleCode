<?php

namespace Vdp;

class PaymentAccountValidationTest extends \PHPUnit_Framework_TestCase {
	
	public function setUp() {
		$this->visaAPIClient = new VisaAPIClient;
		$this->paymentAccountValidation = json_encode ([
		  "acquirerCountryCode" => "840",
		  "acquiringBin" => "408999",
		  "addressVerificationResults" => [
		    "postalCode" => "T4B 3G5",
		    "street" => "801 Metro Center Blv"
		  ],
		  "cardAcceptor" => [
		    "address" => [
		      "city" => "San Francisco",
		      "country" => "USA",
		      "county" => "CA",
		      "state" => "CA",
		      "zipCode" => "94404"
		    ],
		    "idCode" => "111111",
		    "name" => "rohan",
		    "terminalId" => "123"
		  ],
		  "cardCvv2Value" => "672",
		  "cardExpiryDate" => "2018-06",
		  "primaryAccountNumber" => "4957030000313108",
		  "retrievalReferenceNumber" => "015221743720",
		  "systemsTraceAuditNumber" => "743720"
		]);
	}
	
	public function testCardValidation() {
		$baseUrl = "pav/";
		$resourcePath = "v1/cardvalidation";
		$statusCode = $this->visaAPIClient->doMutualAuthCall ( 'post', $baseUrl.$resourcePath, 'Card Validation call', $this->paymentAccountValidation );
		$this->assertEquals($statusCode, "200");
	}
}
