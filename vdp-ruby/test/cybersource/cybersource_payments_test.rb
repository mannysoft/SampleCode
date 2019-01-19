require 'test/unit'
require 'rest-client'
require 'json'
require 'yaml'
require File.expand_path('../../../lib/visa_api_client', __FILE__)

class CybersourcePaymentsTest < Test::Unit::TestCase
  
  def setup
    @config = YAML.load_file('configuration.yml')
    @visa_api_client = VisaAPIClient.new
    @paymentAuthorizationRequest='''{
      "amount": "0",
      "currency": "USD",
      "payment": {
        "cardNumber": "4111111111111111",
        "cardExpirationMonth": "10",
        "cardExpirationYear": "2020"
       }
    }'''
  end
  
  def test_authorize_payments
    base_uri = 'cybersource/'
    resource_path = 'payments/v1/authorizations'
    api_key = @config['apiKey']
    response_code = @visa_api_client.doXPayTokenRequest(base_uri, resource_path, "apikey=#{api_key}", "Payment Authorization Request test", "post", @paymentAuthorizationRequest)
    assert_equal("201", response_code, "Payment Authorization test failed")
  end
end
