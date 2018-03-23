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

describe('MLC Enrollment test', function() {
	var visaAPIClient = new VisaAPIClient();
	var enrollementData = JSON.stringify({
		  "enrollmentMessageType": "enroll",
		  "enrollmentRequest": {
		    "cardholderMobileNumber": "0016504323000",
		    "clientMessageID": config.mlcClientMessageID,
		    "deviceId": config.mlcDeviceId,
		    "issuerId": config.mlcIssuerId,
		    "primaryAccountNumber": config.mlcPrimaryAccountNumber
		  }
		});
	
	it('Card Enrollment Test',function(done) {
		this.timeout(10000);
		var baseUri = 'mlc/';
		var resourcePath = 'enrollment/v1/enrollments';
		visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, enrollementData, 'POST', {}, 
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
