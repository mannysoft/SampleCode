require 'test/unit'
require 'rest-client'
require 'json'
require 'yaml'
require File.expand_path('../../../lib/visa_api_client', __FILE__)

class GetPaymentDataTest < Test::Unit::TestCase
  
  def setup
    @config = YAML.load_file('configuration.yml')
    @visa_api_client = VisaAPIClient.new
  end
  
  def test_getPaymentInfo
    base_uri = 'wallet-services-web/'
    api_key = @config['apiKey']
    resource_path = 'payment/data/{callId}'
    resource_path = resource_path.sub('{callId}',@config['checkoutCallId'])
    response_code = @visa_api_client.doXPayTokenRequest(base_uri, resource_path, "apikey=#{api_key}", "Get Payment Information Test", "get", '')
    assert_equal("200", response_code, "Get Payment Information test failed")
    end
end
