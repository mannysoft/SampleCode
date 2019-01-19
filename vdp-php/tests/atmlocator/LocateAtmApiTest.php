<?php

namespace Vdp;

class LocateAtmApiTest extends \PHPUnit_Framework_TestCase {
	
	public function setUp() {
		$strDate = date('Y-m-d\TH:i:s.z\Z', time());
		$this->visaAPIClient = new VisaAPIClient;
		$this->atmInquiryRequest = json_encode ([
		  "requestData" => [
			    "culture" => "en-US",
			    "distance" => "20",
			    "distanceUnit" => "mi",
			    "location" => [
			      "address" => null,
			      "geocodes" => null,
			      "placeName" => "800 metro center , foster city,ca"
			    ],
			    "metaDataOptions" => 0,
			    "options" => [
			      "findFilters" => [
			        [
			          "filterName" => "PLACE_NAME",
			          "filterValue" => "FORT FINANCIAL CREDIT UNION|ULTRON INC|U.S. BANK"
			        ],
			        [
			          "filterName" => "OPER_HRS",
			          "filterValue" => "C"
			        ],
			        [
			          "filterName" => "AIRPORT_CD",
			          "filterValue" => ""
			        ],
			        [
			          "filterName" => "WHEELCHAIR",
			          "filterValue" => "N"
			        ],
			        [
			          "filterName" => "BRAILLE_AUDIO",
			          "filterValue" => "N"
			        ],
			        [
			          "filterName" => "BALANCE_INQUIRY",
			          "filterValue" => "N"
			        ],
			        [
			          "filterName" => "CHIP_CAPABLE",
			          "filterValue" => "N"
			        ],
			        [
			          "filterName" => "PIN_CHANGE",
			          "filterValue" => "N"
			        ],
			        [
			          "filterName" => "RESTRICTED",
			          "filterValue" => "N"
			        ],
			        [
			          "filterName" => "PLUS_ALLIANCE_NO_SURCHARGE_FEE",
			          "filterValue" => "N"
			        ],
			        [
			          "filterName" => "ACCEPTS_PLUS_SHARED_DEPOSIT",
			          "filterValue" => "N"
			        ],
			        [
			          "filterName" => "V_PAY_CAPABLE",
			          "filterValue" => "N"
			        ],
			        [
			          "filterName" => "READY_LINK",
			          "filterValue" => "N"
			        ]
			      ],
			      "operationName" => "or",
			      "range" => [
			        "count" => 99,
			        "start" => 0
			      ],
			      "sort" => [
			        "direction" => "desc",
			        "primary" => "city"
			      ],
			      "useFirstAmbiguous" => true
			    ]
			  ],
			  "wsRequestHeaderV2" => [
			    "applicationId" => "VATMLOC",
			    "correlationId" => "909420141104053819418",
			    "requestMessageId" => "test12345678",
			    "requestTs" => $strDate,
			    "userBid" => "10000108",
			    "userId" => "CDISIUserID"
			  ]
			]);
	}
	
	public function testAtmInquiry() {
		$baseUrl = "globalatmlocator/";
		$resourcePath = "v1/localatms/atmsinquiry";
		$statusCode = $this->visaAPIClient->doMutualAuthCall ( 'post', $baseUrl.$resourcePath, 'ATM Locator test', $this->atmInquiryRequest );
		$this->assertEquals($statusCode, "200");
	}
}
