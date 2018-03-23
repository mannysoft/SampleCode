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

describe('Visa Consumer Transaction Controls Program Administration test', function() {
	var visaAPIClient = new VisaAPIClient();
	
	it('Retrieve Transaction Type Controls',function(done) {
		this.timeout(10000);
		var baseUri = 'vctc/';
		var resourcePath = 'programadmin/v1/configuration/transactiontypecontrols';
		visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, '', 'GET', {}, 
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
