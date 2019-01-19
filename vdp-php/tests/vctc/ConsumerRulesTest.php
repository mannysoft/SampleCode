<?php

namespace Vdp;

class ConsumerRulesTest extends \PHPUnit_Framework_TestCase {
	
	public function setUp() {
		$this->conf = parse_ini_file ( "configuration.ini", true );
		$this->visaAPIClient = new VisaAPIClient;
		$this->cardRegisterData = json_encode ([
		  "primaryAccountNumber" => $this->conf ['VDP'] ['vctcTestPan']
	]);
	}
	
	public function testRegisterACard() {
		$baseUrl = "vctc/";
		$resourcePath = "customerrules/v1/consumertransactioncontrols";
		$statusCode = $this->visaAPIClient->doMutualAuthCall ( 'post', $baseUrl.$resourcePath, 'Register a card call', $this->cardRegisterData );
		$this->assertTrue(($statusCode == "200" || $statusCode == "201"));
	}
}
