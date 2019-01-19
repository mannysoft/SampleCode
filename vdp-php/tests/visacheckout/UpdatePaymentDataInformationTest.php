<?php

namespace Vdp;

class UpdatePaymentDataInformationTest extends \PHPUnit_Framework_TestCase {
	
	public function setUp() {
		$this->visaAPIClient = new VisaAPIClient;
		$this->conf = parse_ini_file ( "configuration.ini", true );
		$this->updatePaymentInfoRequest = json_encode ([
                          "orderInfo" => [
                          "currencyCode" => "USD",
                          "discount" => "5.25",
                          "eventType" => "Confirm",
                          "giftWrap" => "10.1",
                          "misc" => "3.2",
                          "orderId" => "testorderID",
                          "promoCode" => "testPromoCode",
                          "reason" => "Order Successfully Created",
                          "shippingHandling" => "5.1",
                          "subtotal" => "80.1",
                          "tax" => "7.1",
                          "total" => "101"
                        ]
                     ]);
	}
	
	public function testUpdatePaymentInfo() {
		$baseUrl = "wallet-services-web/";
		$resourcePath = "payment/info/{callId}";
		$resourcePath = str_replace("{callId}",$this->conf ['VDP'] ['checkoutCallId'],$resourcePath);
		$queryString = "apikey=".$this->conf ['VDP'] ['apiKey'];
		$statusCode = $this->visaAPIClient->doXPayTokenCall ( 'put', $baseUrl, $resourcePath, $queryString, 'Update Payment Information Test', $this->updatePaymentInfoRequest);
		$this->assertEquals($statusCode, "200");
	}
}
