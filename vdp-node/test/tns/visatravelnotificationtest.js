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

describe('Visa Travel Notification Service Test', function() {
	var visaAPIClient = new VisaAPIClient();
	var departureDate = new Date();
	var returnDate = new Date();
	returnDate.setDate(returnDate.getDate() + 7);
	var travelNotificationRequest = JSON.stringify({
            "addTravelItinerary": {
            "returnDate" : returnDate.toISOString().replace(/T.*$/, ''),
            "departureDate" : departureDate.toISOString().replace(/T.*$/, ''),
            "destinations": [
                              {
                                "state": "CA",
                                "country": "840"
                              }
                            ],
            "primaryAccountNumbers": config.tnsCardNumbers,
            "userId": "Rajesh",
            "partnerBid": config.tnsPartnerBid
            }
        });
	
	it('Add Travel Itenary Test',function(done) {
		this.timeout(10000);
		var baseUri = 'travelnotificationservice/';
		var resourcePath = 'v1/travelnotification/itinerary';
		visaAPIClient.doMutualAuthRequest(baseUri + resourcePath, travelNotificationRequest, 'POST', {}, 
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
