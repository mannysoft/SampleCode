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

describe('Merchant Locator Test', function() {
    var visaAPIClient = new VisaAPIClient();
    var strDate = new Date().toISOString().replace(/Z/, '');
    var locatorRequest = JSON.stringify({
        "header": {
            "messageDateTime": strDate,
            "requestMessageId": "VCO_GMR_001"
        },
        "searchAttrList": {
            "merchantName": "ALOHA CAFE",
            "merchantCountryCode": "840",
            "latitude": "34.047616",
            "longitude": "-118.239079",
            "distance": "100",
            "distanceUnit": "M"
        },
        "responseAttrList": [
        "GNLOCATOR"
        ],
        "searchOptions": {
            "maxRecords": "2",
            "matchIndicators": "true",
            "matchScore": "true"
        }
    });
    
    it('Merchant Locator API Test',function(done) {
        this.timeout(10000);
        var baseUri = 'merchantlocator/';
        var resourcePath = 'v1/locator';
        visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, locatorRequest, 'POST', {}, 
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
