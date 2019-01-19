<?php

namespace Vdp;

class ManagePortfoliosTest extends \PHPUnit_Framework_TestCase {
	
	public function setUp() {
		$this->conf = parse_ini_file ( "configuration.ini", true );
		$this->visaAPIClient = new VisaAPIClient;
	}
	
	public function testPortfolios() {
		$baseUrl = "vta/";
		$resourcePath = "v3/communities/".$this->conf['VDP'] ['vtaCommunityCode']."/portfolios";
		$statusCode = $this->visaAPIClient->doMutualAuthCall( 'get', $baseUrl.$resourcePath, 'Get Portfolio Details Test', '', array("ServiceId: ".$this->conf['VDP'] ['vtaServiceId'] ));
		$this->assertEquals($statusCode, "200");
	}
}
