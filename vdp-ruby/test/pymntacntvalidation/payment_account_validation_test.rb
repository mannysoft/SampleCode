require 'test/unit'
require 'rest-client'
require 'json'
require 'yaml'
require File.expand_path('../../../lib/visa_api_client', __FILE__)

class PaymentAccountValidationTest < Test::Unit::TestCase
  def setup
    @visa_api_client = VisaAPIClient.new
    @paymentAccountValidation ='''{
    "acquirerCountryCode": "840",
    "acquiringBin": "408999",
    "addressVerificationResults": {
      "postalCode": "T4B 3G5",
      "street": "801 Metro Center Blv"
    },
    "cardAcceptor": {
      "address": {
        "city": "San Francisco",
        "country": "USA",
        "county": "CA",
        "state": "CA",
        "zipCode": "94404"
      },
      "idCode": "111111",
      "name": "rohan",
      "terminalId": "123"
    },
    "cardCvv2Value": "672",
    "cardExpiryDate": "2018-06",
    "primaryAccountNumber": "4957030000313108",
    "retrievalReferenceNumber": "015221743720",
    "systemsTraceAuditNumber": "743720"
  }'''
  end
  
  def test_cardValidation
    base_uri = 'pav/'
    resource_path = 'v1/cardvalidation'
    response_code = @visa_api_client.doMutualAuthRequest("#{base_uri}#{resource_path}", "Payment Account Validation test", "post", @paymentAccountValidation)
    assert_equal("200", response_code, "Payment Account Validation test failed")
  end
end
