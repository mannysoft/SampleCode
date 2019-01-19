var request = require('request');
var fs = require('fs');
var config = require('../../config/configuration.json');
var VisaAPIClient = require('../../libs/visaapiclient.js');
var assert = require('chai').assert;
var randomstring = require('randomstring');

var userId = config.userId ;
var password = config.password;
var keyFile = config.key;
var certificateFile = config.cert;

describe('Visa Direct Push Funds Transactions test', function() {
	var visaAPIClient = new VisaAPIClient();
	var strDate = new Date().toISOString().replace(/\..+/, '');
	var pushFundsRequest = JSON.stringify({
		  "systemsTraceAuditNumber": 350420,
		  "retrievalReferenceNumber": "401010350420",
		  "localTransactionDateTime": strDate,
		  "acquiringBin": 409999,
		  "acquirerCountryCode": "101",
		  "senderAccountNumber": "1234567890123456",
		  "senderCountryCode": "USA",
		  "transactionCurrencyCode": "USD",
		  "senderName": "John Smith",
		  "senderAddress": "44 Market St.",
		  "senderCity": "San Francisco",
		  "senderStateCode": "CA",
		  "recipientName": "Adam Smith",
		  "recipientPrimaryAccountNumber": "4957030420210454",
		  "amount": "112.00",
		  "businessApplicationId": "AA",
		  "transactionIdentifier": 234234322342343,
		  "merchantCategoryCode": 6012,
		  "sourceOfFundsCode": "03",
		  "cardAcceptor": {
		    "name": "John Smith",
		    "terminalId": "13655392",
		    "idCode": "VMT200911026070",
		    "address": {
		      "state": "CA",
		      "county": "081",
		      "country": "USA",
		      "zipCode": "94105"
		    }
		  },
		  "feeProgramIndicator": "123"
		});
	
	it('Push Funds Transaction Test',function(done) {
		this.timeout(10000);
		var baseUri = 'visadirect/';
		var resourcePath = 'fundstransfer/v1/pushfundstransactions';
		visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, pushFundsRequest, 'POST', {}, 
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
