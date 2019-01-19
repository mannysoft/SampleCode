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

describe('Payment Account Attributes General Inquiry Test', function() {
    var visaAPIClient = new VisaAPIClient();
    var generalAttributeInquiry = JSON.stringify({
          "primaryAccountNumber": "4465390000029077"
    });
    
    it('General Attributes Enquiry',function(done) {
        this.timeout(10000);
        var baseUri = 'paai/';
        var resourcePath = 'generalattinq/v1/cardattributes/generalinquiry';
        visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, generalAttributeInquiry, 'POST', {}, 
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
