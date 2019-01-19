<?php

namespace Vdp;

class ManageCustomersTest extends \PHPUnit_Framework_TestCase {
	
	public function setUp() {
		$this->conf = parse_ini_file ( "configuration.ini", true );
		$strDate = date('Y-m-d\TH:i:s.z\Z', time());
		$this->visaAPIClient = new VisaAPIClient;
		
		$this->createCustomersRequest = json_encode ([
				"customer" => [
					"cards" => [
							[
						"address" => json_decode($this->conf['VDP'] ['vtaCreateCustomerAddress']),
						"billCycleDay" => "7",
						"bin" => 431263,
						"cardEnrollmentDate" => $strDate,
						"cardExpiryDate" => $this->conf['VDP'] ['vtaCreateCustomerCardExpiryDate'],
						"cardNickName" => "My Card",
						"cardNumber" => $this->conf['VDP'] ['vtaCreateCustomerCardNumber'],
						"cardSecurityCode" => $this->conf['VDP'] ['vtaCreateCustomerCardSecurityCode'],
							"contactServiceOfferings"=> [
								[
									"contact"=> [
											"contactNickName"=> "testEmail",
											"contactType"=> "Email",
											"contactValue"=> "john@visa.com",
											"isVerified"=> true,
											"lastUpdateDateTime"=> $strDate,
											"mobileCountryCode"=> null,
											"mobileVerificationCode"=> null,
											"mobileVerificationCodeDate"=> date('Y-m-d\TH:i:s', time()),
											"platform"=> "None",
											"preferredEmailFormat"=> "Html",
											"securityPhrase"=> null,
											"status"=> "Active"
										],
										"serviceOfferings"=> [
										[
											"isActive"=> true,
											"offeringId"=> "Threshold",
											"offeringProperties"=>[
												[
													"key"=> "ThresholdAmount",
													"value"=> "10"
										  		]
											 ]
										 ],
										 [
											"isActive"=> true,
											"offeringId"=> "CrossBorder",
											"offeringProperties"=> [ 
													[
														"key"=> "ThresholdAmount",
														"value"=> "10"
													] 
												  ]
											],
											[
												"isActive"=> true,
												"offeringId"=> "Declined",
												"offeringProperties"=> [   
														[
															"key"=> "ThresholdAmount",
															"value"=> "10"
														]
													  ]		
											],
											[
												"isActive"=> true,
												"offeringId"=> "CardNotPresent",
												"offeringProperties"=> [ 
														[
															"key"=> "ThresholdAmount",
															"value"=> "10"
														]
													 ]
											]
										]
									]
							],							
							"isActive" => true,
							"lastFour" => $this->conf['VDP'] ['vtaCreateCustomerLastFour'],
							"nameOnCard" => "Mradul",
							"paused" => false,
							"portfolioNum" =>  $this->conf['VDP'] ['vtaPortfolioNumber'],
							"previousCardNumber" => null,
							"productId" => null,
							"productIdDescription" => "Credit",
							"productType" => "Credit",
							"productTypeExtendedCode" => "Credit",
							"rpin" => null
							]
						],
						"communityCode"=> $this->conf['VDP'] ['vtaCommunityCode'],
        				"contacts"=> [
        				  [
                			"contactNickName"=> "testEmail",
                			"contactType"=> "Email",
                			"contactValue"=> "john@visa.com",
                			"isVerified"=> true,
                			"lastUpdateDateTime"=> $strDate ,
                			"mobileCountryCode"=> null,
                			"mobileVerificationCode"=> null,
                			"mobileVerificationCodeDate"=> date('Y-m-d\TH:i:s', time()),
                			"platform"=> "None",
                			"preferredEmailFormat"=> "Html",
                			"securityPhrase"=> null,
                			"status"=> "Active"
        				  ]
        				],
        				"customerEnrollmentDate"=> $strDate,
        				"customerId"=> "a1bb6fe1-ea64-4269-b29d-169aebd8780a",
        				"firstName"=> "James",
       				    "isActive"=> true,
        				"lastName"=> "Bond",
        				"preferredCountryCode"=> $this->conf['VDP'] ['vtaCreateCustomerPreferedCountryCode'],
       			 		"preferredCurrencyCode"=> $this->conf['VDP'] ['vtaCreateCustomerPreferedCurrencyCode'],
        				"preferredFuelAmount"=> "75",
        				"preferredLanguage"=> $this->conf['VDP'] ['vtaCreateCustomerPreferedLanguage'],
        				"preferredTimeZone"=> $this->conf['VDP'] ['vtaCreateCustomerPreferedTimeZone'],
        				"preferredTipPercentage"=> "15"
				]
		]);
	}
	
	public function testGetCustomerDetails() {
		$baseUrl = "vta/";
		$resourcePath = "v3/communities/".$this->conf['VDP'] ['vtaCommunityCode']."/customers";
		$statusCode = $this->visaAPIClient->doMutualAuthCall( 'post', $baseUrl.$resourcePath, 'Create Customers Test', $this->createCustomersRequest, array("ServiceId: ".$this->conf['VDP'] ['vtaServiceId'] ));
		$this->assertEquals($statusCode, "201");
	}
}
