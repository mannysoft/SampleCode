<?php

namespace Vdp;

class MVisaTest extends \PHPUnit_Framework_TestCase {
	
	public function setUp() {
		$this->visaAPIClient = new VisaAPIClient;
		$strDate = date('Y-m-d\TH:i:s', time());
		$this->mVisaTransactionRequest = json_encode ([
		  "acquirerCountryCode" => "643",
		  "acquiringBin" => "400171",
		  "amount" => "124.05",
		  "businessApplicationId" => "CI",
		  "cardAcceptor" => [
		    "address" => [
		      "city" => "Bangalore",
		      "country" => "IND"
		    ],
		    "idCode" => "ID-Code123",
		    "name" => "Card Accpector ABC"
		  ],
		  "localTransactionDateTime" => $strDate,
		  "merchantCategoryCode" => "4829",
		  "recipientPrimaryAccountNumber" => "4123640062698797",
		  "retrievalReferenceNumber" => "430000367618",
		  "senderAccountNumber" => "4541237895236",
		  "senderName" => "Mohammed Qasim",
		  "senderReference" => "1234",
		  "systemsTraceAuditNumber" => "313042",
		  "transactionCurrencyCode" => "USD",
		  "transactionIdentifier" => "381228649430015"
		]);
	}
	
	public function testMVisaTransactions() {
		$baseUrl = "visadirect/";
		$resourcePath = "mvisa/v1/cashinpushpayments";
		$statusCode = $this->visaAPIClient->doMutualAuthCall ( 'post', $baseUrl.$resourcePath, 'M Visa Transaction Test', $this->mVisaTransactionRequest );
		$this->assertEquals($statusCode, "200");
	}
}
