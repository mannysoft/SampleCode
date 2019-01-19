<?php

namespace Vdp;

class ProgramAdministrationTest extends \PHPUnit_Framework_TestCase {
	
	public function setUp() {
		$this->visaAPIClient = new VisaAPIClient;
	}
	
	public function testRetreiveTransactionTypeControls() {
		$baseUrl = "vctc/";
		$resourcePath = "programadmin/v1/configuration/transactiontypecontrols";
		$statusCode = $this->visaAPIClient->doMutualAuthCall ( 'get', $baseUrl.$resourcePath, 'Retrieve Transaction Type Control call', '' );
		$this->assertEquals($statusCode, "200");
	}
}
