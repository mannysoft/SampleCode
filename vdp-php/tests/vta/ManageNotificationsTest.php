<?php

namespace Vdp;

class ManageNotificationsTest extends \PHPUnit_Framework_TestCase {
	
	public function setUp() {
		$this->conf = parse_ini_file ( "configuration.ini", true );
		$this->visaAPIClient = new VisaAPIClient;
		$this->notificationSubscriptionRequest = json_encode ([
                         "contactType"=> $this->conf['VDP'] ['vtaNotificationContactType'],
                         "contactValue"=> "john@visa.com",
                         "emailFormat"=> "None",
                         "last4"=> $this->conf['VDP'] ['vtaCreateCustomerLastFour'],
                         "phoneCountryCode"=> "en-us",
                         "platform"=> "None",
                         "preferredLanguageCode"=> $this->conf['VDP'] ['vtaPreferredLanguageCode'],
                         "serviceOffering"=> "WelcomeMessage",
                         "serviceOfferingSubType"=> "WelcomeMessage",
                         "substitutions"=> json_decode('{}')
                      ]);
	}
	
	public function testNotificationSubscription() {
		$baseUrl = "vta/";
		$resourcePath = "v3/communities/".$this->conf['VDP'] ['vtaCommunityCode']."/portfolios/".$this->conf['VDP'] ['vtaPortfolioNumber']."/customers/".$this->conf['VDP'] ['vtaCustomerId']."/notifications";
		$statusCode = $this->visaAPIClient->doMutualAuthCall( 'post', $baseUrl.$resourcePath, 'Notification Subscriptions Test', $this->notificationSubscriptionRequest, array("ServiceId: ".$this->conf['VDP'] ['vtaServiceId'] ));
		$this->assertEquals($statusCode, "201");
	}
}
