<?php

namespace Vdp;

class CybersourcePaymentsTest extends \PHPUnit_Framework_TestCase {
	
	public function setUp() {
		$this->visaAPIClient = new VisaAPIClient;
		$this->paymentAuthorizationRequest = json_encode ( [ 
	    'amount' => '0',
	    'currency' => 'USD',
	    'payment' => [
	      'cardNumber'=> '4111111111111111',
	      'cardExpirationMonth' => '10',
	      'cardExpirationYear' => '2020'
	    ]
	    ] );
	}
	public function testPaymentAuthorizations() {
		$baseUrl = "cybersource/";
		$resourcePath = "payments/v1/authorizations";
		$queryString = "apikey=".$this->visaAPIClient->conf ['VDP'] ['apiKey'];
		$statusCode = $this->visaAPIClient->doXPayTokenCall ( 'post', $baseUrl, $resourcePath, $queryString, 'Cybersource Payments Test', $this->paymentAuthorizationRequest );
		$this->assertEquals($statusCode, "201");
	}
}
