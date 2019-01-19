<?php

namespace Vdp;

class ForeignExchangeTest extends \PHPUnit_Framework_TestCase {
	
	public function setUp() {
		$this->visaAPIClient = new VisaAPIClient;
		$this->foreignExchangeRequest = json_encode ( [
		  "acquirerCountryCode" => "840",
		  "acquiringBin" => "408999",
		  "cardAcceptor" => [
		    "address" => [
		      "city" => "San Francisco",
		      "country" => "USA",
		      "county" => "San Mateo",
		      "state" => "CA",
		      "zipCode" => "94404"
		    ],
		    "idCode" => "ABCD1234ABCD123",
		    "name" => "ABCD",
		    "terminalId" => "ABCD1234"
		  ],
		  "destinationCurrencyCode" => "826",
		  "markUpRate" => "1",
		  "retrievalReferenceNumber" => "201010101031",
		  "sourceAmount" => "100.00",
		  "sourceCurrencyCode" => "840",
		  "systemsTraceAuditNumber" => "350421"
		] );
	}
	public function testForeignExchangeRates() {
		$baseUrl = "forexrates/";
		$resourcePath = "v1/foreignexchangerates";
		$statusCode = $this->visaAPIClient->doMutualAuthCall ( 'post', $baseUrl.$resourcePath, 'Foreign Exchange call', $this->foreignExchangeRequest );
		$this->assertEquals($statusCode, "200");
	}
}
