<?php

namespace Vdp;

class LocationUpdateTest extends \PHPUnit_Framework_TestCase {
	
	public function setUp() {
		$this->conf = parse_ini_file ( "configuration.ini", true );
		$this->visaAPIClient = new VisaAPIClient;
		$strDate = date('Y-m-d\TH:i:s.z\Z', time());
		$this->locationsRequestBody = json_encode ( [
		  "accuracy" => "5000",
		  "cloudNotificationKey" => "03e3ae03-a627-4241-bad6-58f811c18e46",
		  "cloudNotificationProvider" => "1",
		  "deviceId" => $this->conf ['VDP'] ['mlcDeviceId'],
		  "deviceLocationDateTime" => $strDate,
		  "geoLocationCoordinate" => [
		    "latitude" => "37.558546",
		    "longitude" => "-122.271079"
		  ],
		  "header" => [
		    "messageDateTime" => $strDate,
		    "messageId" => $this->conf ['VDP'] ['mlcMessageId']
		  ],
		  "issuerId" => $this->conf ['VDP'] ['mlcIssuerId'],
		  "provider" => "1",
		  "source" => "2"
		] );
	}
	
	public function testlocations() {
		$baseUrl = "mlc/";
		$resourcePath = "locationupdate/v1/locations";
		$statusCode = $this->visaAPIClient->doMutualAuthCall ( 'post', $baseUrl.$resourcePath, 'Location Update Test', $this->locationsRequestBody );
		$this->assertEquals($statusCode, "200");
	}
}
