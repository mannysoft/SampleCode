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

describe('Foreign Exchange API test', function() {
    var visaAPIClient = new VisaAPIClient();
    var foreignExchangeRequest = JSON.stringify({
          "acquirerCountryCode": "840",
          "acquiringBin": "408999",
          "cardAcceptor": {
            "address": {
              "city": "San Francisco",
              "country": "USA",
              "county": "San Mateo",
              "state": "CA",
              "zipCode": "94404"
            },
            "idCode": "ABCD1234ABCD123",
            "name": "ABCD",
            "terminalId": "ABCD1234"
          },
          "destinationCurrencyCode": "826",
          "markUpRate": "1",
          "retrievalReferenceNumber": "201010101031",
          "sourceAmount": "100.00",
          "sourceCurrencyCode": "840",
          "systemsTraceAuditNumber": "350421"
        });
    
    it('Foreign Exchange Rates Test',function(done) {
        this.timeout(10000);
        var baseUri = 'forexrates/';
        var resourcePath = 'v1/foreignexchangerates';
        visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, foreignExchangeRequest, 'POST', {}, 
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
