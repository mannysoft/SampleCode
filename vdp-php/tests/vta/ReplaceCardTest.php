<?php

namespace Vdp;

class ReplaceCardTest extends \PHPUnit_Framework_TestCase {
	
	public function setUp() {
		$strDate = date('Y-m-d\TH:i:s.z\Z', time());
		$this->conf = parse_ini_file ( "configuration.ini", true );
		$this->visaAPIClient = new VisaAPIClient;
		$this->replaceCardsRequest = json_encode ([
            "communityCode" => $this->conf['VDP'] ['vtaCommunityCode'],
            "newCard" => [
            	"address" => json_decode($this->conf['VDP'] ['vtaReplaceCardNewAddress']),
                "billCycleDay" => "22",
                "bin" => null,
                "cardEnrollmentDate" => "2016-06-10T08:36:59+00:00",
                "cardExpiryDate" => $this->conf['VDP'] ['vtaReplaceCardExpiryDate'],
                "cardNickName" => "My Visa 3",
                "cardNumber" => $this->conf['VDP'] ['vtaReplaceCardNumber'],
                "cardSecurityCode" => $this->conf['VDP'] ['vtaReplaceCardSecurityCode'],
                "isActive" => true,
                "lastFour" => $this->conf['VDP'] ['vtaReplaceCardLastFour'],
                "nameOnCard" => "Mradul",
                "paused" => false,
                "portfolioNum" =>  $this->conf['VDP'] ['vtaPortfolioNumber'],
                "previousCardNumber" => null,
                "productId" => null,
                "productIdDescription" => "Credit",
                "productType" => "Credit",
                "productTypeExtendedCode" => "123",
                "rpin" => null
            ],
            "oldCard" => [
                "address" => json_decode($this->conf['VDP'] ['vtaCreateCustomerAddress']),
                "billCycleDay" => "22",
                "bin" => null,
                "cardEnrollmentDate" => "2016-06-10T08:36:59+00:00",
                "cardExpiryDate" => $this->conf['VDP'] ['vtaCreateCustomerCardExpiryDate'],
                "cardNickName" => "My Visa 3",
                "cardNumber" => $this->conf['VDP'] ['vtaCreateCustomerCardNumber'],
                "cardSecurityCode" =>  $this->conf['VDP'] ['vtaCreateCustomerCardSecurityCode'],
            	"isActive" => true,
           	 	"lastFour" =>  $this->conf['VDP'] ['vtaCreateCustomerLastFour'],
            	"nameOnCard" => "ddd",
            	"paused" => false,
            	"portfolioNum" =>  $this->conf['VDP'] ['vtaPortfolioNumber'],
            	"previousCardNumber" => null,
            	"productId" => null,
            	"productIdDescription" => "Credit",
            	"productType" => "Credit",
            	"productTypeExtendedCode" => "123",
            	"rpin" => null
          ]
		]);
	}
	
	public function testReplaceCards() {
		$baseUrl = "vta/";
		$resourcePath = "v3/communities/".$this->conf['VDP'] ['vtaCommunityCode']."/cards";
		$statusCode = $this->visaAPIClient->doMutualAuthCall( 'post', $baseUrl.$resourcePath, 'Replace a card test', $this->replaceCardsRequest, array("ServiceId: ".$this->conf['VDP'] ['vtaServiceId'] ));
		$this->assertEquals($statusCode, "201");
	}
}
