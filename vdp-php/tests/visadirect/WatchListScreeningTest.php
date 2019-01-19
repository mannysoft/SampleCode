<?php

namespace Vdp;

class WatchListScreeningTest extends \PHPUnit_Framework_TestCase {
	
	public function setUp() {
		$this->visaAPIClient = new VisaAPIClient;
		$this->watchListInquiry = json_encode ([
		  "acquirerCountryCode" => "840",
		  "acquiringBin" => "408999",
		  "address" => [
		    "city" => "Bangalore",
		    "cardIssuerCountryCode" => "IND"
		  ],
		  "referenceNumber" => "430000367618",
		  "name" => "Mohammed Qasim"
		]);
	}
	
	public function testWatchListInquiry() {
		$baseUrl = "visadirect/";
		$resourcePath = "watchlistscreening/v1/watchlistinquiry";
		$statusCode = $this->visaAPIClient->doMutualAuthCall ( 'post', $baseUrl.$resourcePath, 'Watch List Inquiry Test', $this->watchListInquiry );
		$this->assertEquals($statusCode, "200");
	}
}
