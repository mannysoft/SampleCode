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

describe('Visa Direct Watch List Screening test', function() {
	var visaAPIClient = new VisaAPIClient();
	var watchListInquiry = JSON.stringify({
		  "acquirerCountryCode": "840",
		  "acquiringBin": "408999",
		  "address": {
		    "city": "Bangalore",
		    "cardIssuerCountryCode": "IND"
		  },
		  "referenceNumber": "430000367618",
		  "name": "Mohammed Qasim"
		});
	
	it('Watch List Inquiry Test',function(done) {
		this.timeout(10000);
		var baseUri = 'visadirect/';
		var resourcePath = 'watchlistscreening/v1/watchlistinquiry';
		visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, watchListInquiry, 'POST', {}, 
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
