var request = require('request');
var fs = require('fs');
var config = require('../../config/configuration.json');
var VisaAPIClient = require('../../libs/visaapiclient.js');
var assert = require('chai').assert;
var randomstring = require('randomstring');

var req = request.defaults();
var userId = config.userId ;
var password = config.password;
var keyFile = config.key;
var certificateFile = config.cert;

describe('Visa Direct M Visa Test', function() {
	var visaAPIClient = new VisaAPIClient();
	var strDate = new Date().toISOString().replace(/Z/, '').replace(/\..+/, '');
	var mVisaTransactionRequest = JSON.stringify({
		  "acquirerCountryCode": "643",
		  "acquiringBin": "400171",
		  "amount": "124.05",
		  "businessApplicationId": "CI",
		  "cardAcceptor": {
		    "address": {
		      "city": "Bangalore",
		      "country": "IND"
		    },
		    "idCode": "ID-Code123",
		    "name": "Card Accpector ABC"
		  },
		  "localTransactionDateTime": strDate,
		  "merchantCategoryCode": "4829",
		  "recipientPrimaryAccountNumber": "4123640062698797",
		  "retrievalReferenceNumber": "430000367618",
		  "senderAccountNumber": "4541237895236",
		  "senderName": "Mohammed Qasim",
		  "senderReference": "1234",
		  "systemsTraceAuditNumber": "313042",
		  "transactionCurrencyCode": "USD",
		  "transactionIdentifier": "381228649430015"
		});
	
	it('M Visa Transaction Test',function(done) {
		this.timeout(10000);
		var baseUri = 'visadirect/';
		var resourcePath = 'mvisa/v1/cashinpushpayments';
		visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, mVisaTransactionRequest, 'POST', {}, 
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
