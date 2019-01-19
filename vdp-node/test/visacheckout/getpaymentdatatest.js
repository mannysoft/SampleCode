var request = require('request');
var VisaAPIClient = require('../../libs/visaapiclient.js');
var config = require('../../config/configuration.json');
var assert = require('chai').assert;
var req = request.defaults();
var randomstring = require('randomstring');

describe('Get Payment Data', function() {
	var visaAPIClient = new VisaAPIClient();

	it('Get Payment Information Test',function(done){
		this.timeout(10000);
		var apiKey = config.apiKey;
		var baseUri = 'wallet-services-web/';
		var resourcePath = 'payment/data/{callId}';
		resourcePath = resourcePath.replace('{callId}', config.checkoutCallId);
		var queryParams = 'apikey=' + apiKey;
		visaAPIClient.doXPayRequest(baseUri, resourcePath, queryParams, '', 'GET', {}, 
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
