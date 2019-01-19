<?php

namespace Vdp;

class CardholderEnrollmentTest extends \PHPUnit_Framework_TestCase {
	
	public function setUp() {
		$this->conf = parse_ini_file ( "configuration.ini", true );
		$this->visaAPIClient = new VisaAPIClient;
		$this->enrollementData = json_encode ([
		  "enrollmentMessageType" => "enroll",
		  "enrollmentRequest" => [
		    "cardholderMobileNumber" => "0016504323000",
		    "clientMessageID" => $this->conf ['VDP'] ['mlcClientMessageID'],
		    "deviceId" => $this->conf ['VDP'] ['mlcDeviceId'],
		    "issuerId" => $this->conf ['VDP'] ['mlcIssuerId'],
		    "primaryAccountNumber" => $this->conf ['VDP'] ['mlcPrimaryAccountNumber']
		  ]
		]);
	}
	
	public function testCardEnrollment() {
		$baseUrl = "mlc/";
		$resourcePath = "enrollment/v1/enrollments";
		$statusCode = $this->visaAPIClient->doMutualAuthCall ( 'post', $baseUrl.$resourcePath, 'MLC Card Enrollement Test', $this->enrollementData );
		$this->assertEquals($statusCode, "200");
	}
}
