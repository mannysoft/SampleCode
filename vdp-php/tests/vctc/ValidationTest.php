<?php

namespace Vdp;

class ValidationTest extends \PHPUnit_Framework_TestCase {
	
	public function setUp() {
		$this->visaAPIClient = new VisaAPIClient;
	}
	
	public function testRetreiveListofDecisionRecords() {
		$baseUrl = "vctc/";
		$resourcePath = "validation/v1/decisions/history";
		$queryParams = "?limit=1&page=1";
		$statusCode = $this->visaAPIClient->doMutualAuthCall ( 'get', $baseUrl.$resourcePath.$queryParams, 'Retrieve List of Recent Decision Records call', '' );
		$this->assertEquals($statusCode, "200");
	}
}
