<?php

namespace Vdp;

class FundsTransferAttributeTest extends \PHPUnit_Framework_TestCase {
	
	public function setUp() {
		$this->visaAPIClient = new VisaAPIClient;
		$this->fundsTransferInquiry = json_encode ( [
		  "acquirerCountryCode" => "840",
		  "acquiringBin" => "408999",
		  "primaryAccountNumber" => "4957030420210512",
		  "retrievalReferenceNumber" => "330000550000",
		  "systemsTraceAuditNumber" => "451006"
		] );
	}
	public function testFundsTransferEnquiry() {
		$baseUrl = "paai/";
		$resourcePath = "fundstransferattinq/v1/cardattributes/fundstransferinquiry";
		$statusCode = $this->visaAPIClient->doMutualAuthCall ( 'post', $baseUrl.$resourcePath, 'Funds Transfer Inquiry call', $this->fundsTransferInquiry );
		$this->assertEquals($statusCode, "200");
	}
}
