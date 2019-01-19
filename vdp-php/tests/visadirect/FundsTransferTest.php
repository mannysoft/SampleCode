<?php

namespace Vdp;

class FundsTransferTest extends \PHPUnit_Framework_TestCase {
	
	public function setUp() {
		$this->visaAPIClient = new VisaAPIClient;
		$strDate = date('Y-m-d\TH:i:s', time());
		$this->fundsTransferRequest = json_encode ( [ 
				'systemsTraceAuditNumber' => 300259,
				'retrievalReferenceNumber' => '407509300259',
				'localTransactionDateTime' => $strDate,
				'acquiringBin' => 409999,
				'acquirerCountryCode' => '101',
				'senderPrimaryAccountNumber' => '4957030100009952',
				'senderCardExpiryDate' => '2020-03',
				'senderCurrencyCode' => 'USD',
				'amount' => '110',
				'surcharge' => '2.00',
				'cavv' => '0000010926000071934977253000000000000000',
				'foreignExchangeFeeTransaction' => '10.00',
				'businessApplicationId' => 'AA',
				'merchantCategoryCode' => 6012,
				'cardAcceptor' => [ 
						'name' => 'Saranya',
						'terminalId' => '365539',
						'idCode' => 'VMT200911026070',
						'address' => [ 
								'state' => 'CA',
								'county' => '081',
								'country' => 'USA',
								'zipCode' => '94404' 
						] 
				],
				'magneticStripeData' => [ 
						'track1Data' => '1010101010101010101010101010' 
				],
				'pointOfServiceData' => [ 
						'panEntryMode' => '90',
						'posConditionCode' => '0',
						'motoECIIndicator' => '0' 
				],
				'pointOfServiceCapability' => [ 
						'posTerminalType' => '4',
						'posTerminalEntryCapability' => '2' 
				],
				'feeProgramIndicator' => '123' 
		] );
	}
	
	public function testPullFunds() {
		$baseUrl = "visadirect/";
		$resourcePath = "fundstransfer/v1/pullfundstransactions";
		$statusCode = $this->visaAPIClient->doMutualAuthCall ( 'post', $baseUrl.$resourcePath, 'Push Funds Transaction Test', $this->fundsTransferRequest );
		$this->assertEquals($statusCode, "200");
	}
}
