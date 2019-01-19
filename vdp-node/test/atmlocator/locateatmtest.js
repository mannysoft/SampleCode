var request = require('request');
var fs = require('fs');
var VisaAPIClient = require('../../libs/visaapiclient.js');
var config = require('../../config/configuration.json');
var assert = require('chai').assert;
var randomstring = require('randomstring');

var req = request.defaults();
var userId = config.userId ;
var password = config.password;
var keyFile = config.key;
var certificateFile = config.cert;

describe('ATM Locator test', function() {
	var visaAPIClient = new VisaAPIClient();
	var strDate = new Date().toISOString();
	var atmInquiryRequest = JSON.stringify({
		"requestData": {
			"culture": "en-US",
			"distance": "20",
			"distanceUnit": "mi",
			"location": {
				"address": null,
				"geocodes": null,
				"placeName": "800 metro center , foster city,ca"
			},
			"metaDataOptions": 0,
			"options": {
				"findFilters": [
				                {
				                	"filterName": "PLACE_NAME",
				                	"filterValue": "FORT FINANCIAL CREDIT UNION|ULTRON INC|U.S. BANK"
				                },
				                {
				                	"filterName": "OPER_HRS",
				                	"filterValue": "C"
				                },
				                {
				                	"filterName": "AIRPORT_CD",
				                	"filterValue": ""
				                },
				                {
				                	"filterName": "WHEELCHAIR",
				                	"filterValue": "N"
				                },
				                {
				                	"filterName": "BRAILLE_AUDIO",
				                	"filterValue": "N"
				                },
				                {
				                	"filterName": "BALANCE_INQUIRY",
				                	"filterValue": "N"
				                },
				                {
				                	"filterName": "CHIP_CAPABLE",
				                	"filterValue": "N"
				                },
				                {
				                	"filterName": "PIN_CHANGE",
				                	"filterValue": "N"
				                },
				                {
				                	"filterName": "RESTRICTED",
				                	"filterValue": "N"
				                },
				                {
				                	"filterName": "PLUS_ALLIANCE_NO_SURCHARGE_FEE",
				                	"filterValue": "N"
				                },
				                {
				                	"filterName": "ACCEPTS_PLUS_SHARED_DEPOSIT",
				                	"filterValue": "N"
				                },
				                {
				                	"filterName": "V_PAY_CAPABLE",
				                	"filterValue": "N"
				                },
				                {
				                	"filterName": "READY_LINK",
				                	"filterValue": "N"
				                }
				                ],
				                "operationName": "or",
				                "range": {
				                	"count": 99,
				                	"start": 0
				                },
				                "sort": {
				                	"direction": "desc",
				                	"primary": "city"
				                },
				                "useFirstAmbiguous": true
			}
		},
		"wsRequestHeaderV2": {
			"applicationId": "VATMLOC",
			"correlationId": "909420141104053819418",
			"requestMessageId": "test12345678",
			"requestTs": strDate,
			"userBid": "10000108",
			"userId": "CDISIUserID"
		}
	});

	it('Locate ATM Test', function(done) {
		this.timeout(10000);
		var baseUri = 'globalatmlocator/';
		var resourcePath = 'v1/localatms/atmsinquiry';
		visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, atmInquiryRequest, 'POST', {}, 
		function(err, responseCode) {
            if(!err) {
                assert.equal(responseCode, 200);
            } else {
                assert(false);
            }
            done();
        });
	});
});
