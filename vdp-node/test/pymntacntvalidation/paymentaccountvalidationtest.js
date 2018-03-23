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

describe('Payment Account Validation Test', function() {
	var visaAPIClient = new VisaAPIClient();
	var paymentAccountValidation = JSON.stringify({
		  "acquirerCountryCode": "840",
		  "acquiringBin": "408999",
		  "addressVerificationResults": {
		    "postalCode": "T4B 3G5",
		    "street": "801 Metro Center Blv"
		  },
		  "cardAcceptor": {
		    "address": {
		      "city": "San Francisco",
		      "country": "USA",
		      "county": "CA",
		      "state": "CA",
		      "zipCode": "94404"
		    },
		    "idCode": "111111",
		    "name": "rohan",
		    "terminalId": "123"
		  },
		  "cardCvv2Value": "672",
		  "cardExpiryDate": "2018-06",
		  "primaryAccountNumber": "4957030000313108",
		  "retrievalReferenceNumber": "015221743720",
		  "systemsTraceAuditNumber": "743720"
		});
	
	it('Card Validation Test',function(done) {
		this.timeout(10000);
		var baseUri = 'pav/';
		var resourcePath = 'v1/cardvalidation';
		visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, paymentAccountValidation, 'POST', {}, 
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
