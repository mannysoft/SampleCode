<?php

namespace Vdp;

class ManageCommunitiesTest extends \PHPUnit_Framework_TestCase {
	
	public function setUp() {
		$this->conf = parse_ini_file ( "configuration.ini", true );
		$this->visaAPIClient = new VisaAPIClient;
	}
	
	public function testGetCommunities() {
		$baseUrl = "vta/";
		$resourcePath = "v3/communities";
		$statusCode = $this->visaAPIClient->doMutualAuthCall( 'get', $baseUrl.$resourcePath, 'Get Communities Test', '', array("ServiceId: ".$this->conf['VDP'] ['vtaServiceId'] ));
		$this->assertEquals($statusCode, "200");
	}
}
