<?php

namespace Vdp;

class VisaTravelNotificationService extends \PHPUnit_Framework_TestCase {
	
	public function setUp() {
		$this->conf = parse_ini_file ( "configuration.ini", true );
		$this->visaAPIClient = new VisaAPIClient;
		$departureDate = date('Y-m-d', time());
		$returnDate = strtotime("+10 day", strtotime($departureDate));
		$returnDate = date('Y-m-d', $returnDate);
		$this->locatorRequest = json_encode ([
            "addTravelItinerary" => [
            "returnDate" => $returnDate,
            "departureDate" => $departureDate,
            "destinations" => [
                              [
                                "state" => "CA",
                                "country" => "840"
                              ]
                            ],
            "primaryAccountNumbers" => json_decode($this->conf ['VDP'] ['tnsCardNumbers']),
            "userId" => "Rajesh",
            "partnerBid" => $this->conf ['VDP'] ['tnsPartnerBid']
            ]
        ]);
	}
	
	public function testAddTravelItenary() {
		$baseUrl = "travelnotificationservice/";
		$resourcePath = "v1/travelnotification/itinerary";
		$statusCode = $this->visaAPIClient->doMutualAuthCall ( 'post', $baseUrl.$resourcePath, 'Add Travel Itenary Test', $this->locatorRequest );
		$this->assertEquals($statusCode, "200");
	}
}
