require 'test/unit'
require 'rest-client'
require 'json'
require 'yaml'
require File.expand_path('../../../lib/visa_api_client', __FILE__)

class ForeignExchangeTest < Test::Unit::TestCase
  def setup
    @visa_api_client = VisaAPIClient.new
    @foreignExchangeRequest ='''{
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
  }'''
  end
  
  def test_foreignExchange
    base_uri = 'forexrates/'
    resource_path = 'v1/foreignexchangerates'
    response_code = @visa_api_client.doMutualAuthRequest("#{base_uri}#{resource_path}", "Foreign Exchange test", "post", @foreignExchangeRequest)
    assert_equal("200", response_code, "Foreign Exchange test failed")
  end
end
