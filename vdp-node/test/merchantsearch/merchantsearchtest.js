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

describe('Merchant Search Test', function() {
    var visaAPIClient = new VisaAPIClient();
    var strDate = new Date().toISOString().replace(/Z/, '');
    var searchRequest = JSON.stringify({
        "header": {
            "messageDateTime": strDate,
            "requestMessageId": "CDISI_GMR_001",
            "startIndex": "1"
        },
     "searchAttrList": {
        "visaMerchantId":"11687107",
        "visaStoreId":"125861096",
        "merchantName":"ALOHA CAFE",
        "merchantCountryCode":"840",
        "merchantCity": "LOS ANGELES",
        "merchantState": "CA",
        "merchantPostalCode": "90012",
        "merchantStreetAddress": "410 E 2ND ST", 
        "businessRegistrationId":"196007747",
        "acquirerCardAcceptorId":"191642760469222",            
        "acquiringBin":"486168"
     },
    "responseAttrList": ["GNSTANDARD"],
    
    "searchOptions": {
        "maxRecords": "2",
        "matchIndicators" :"true",
        "matchScore": "true"
    }
   });
    
    it('Merchant Search API Test',function(done) {
        this.timeout(10000);
        var baseUri = 'merchantsearch/';
        var resourcePath = 'v1/search';
        visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, searchRequest, 'POST', {}, 
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
