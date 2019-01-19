require 'test/unit'
require 'rest-client'
require 'json'
require 'yaml'
require File.expand_path('../../../lib/visa_api_client', __FILE__)

class WatchListScreening < Test::Unit::TestCase
  def setup
    @visa_api_client = VisaAPIClient.new
    @watchListInquiry ='''{
    "acquirerCountryCode": "840",
    "acquiringBin": "408999",
    "address": {
      "city": "Bangalore",
      "cardIssuerCountryCode": "IND"
    },
    "referenceNumber": "430000367618",
    "name": "Mohammed Qasim"
  }'''
  end
  
  def test_watchListInquiry
    base_uri = 'visadirect/'
    resource_path = 'watchlistscreening/v1/watchlistinquiry'
    response_code = @visa_api_client.doMutualAuthRequest("#{base_uri}#{resource_path}", "Watch List Inquiry call test", "post", @watchListInquiry)
    assert_equal("200", response_code, "Watch List Inquiry test failed")
  end
end
