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

describe('Visa Consumer Transaction Controls Validation API test', function() {
	var visaAPIClient = new VisaAPIClient();
	
	it('Retrieve List of Recent Decision Records',function(done) {
		this.timeout(10000);
		var baseUri = 'vctc/';
		var resourcePath = 'validation/v1/decisions/history';
		var queryParam = '?limit=1&page=1';
		visaAPIClient.doMutualAuthRequest(baseUri + resourcePath + queryParam, '', 'GET', {}, 
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
