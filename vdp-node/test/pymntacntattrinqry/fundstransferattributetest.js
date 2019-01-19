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

describe('Payment Account Attributes Funds Transfer Test', function() {
    var visaAPIClient = new VisaAPIClient();
    var fundsTransferInquiry = JSON.stringify({
          "acquirerCountryCode": "840",
          "acquiringBin": "408999",
          "primaryAccountNumber": "4957030420210512",
          "retrievalReferenceNumber": "330000550000",
          "systemsTraceAuditNumber": "451006"
        });
    
    it('Funds Transfer Inquiry Test',function(done) {
        this.timeout(10000);
        var baseUri = 'paai/';
        var resourcePath = 'fundstransferattinq/v1/cardattributes/fundstransferinquiry';
        visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, fundsTransferInquiry, 'POST', {}, 
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
