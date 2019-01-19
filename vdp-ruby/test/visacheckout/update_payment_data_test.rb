require 'test/unit'
require 'rest-client'
require 'json'
require 'yaml'
require File.expand_path('../../../lib/visa_api_client', __FILE__)

class UpdatePaymentDataTest < Test::Unit::TestCase
  
  def setup
    @config = YAML.load_file('configuration.yml')
    @visa_api_client = VisaAPIClient.new
    @updatePaymentInfoRequest = '''{
      "orderInfo": {
      "currencyCode": "USD",
      "discount": "5.25",
      "eventType": "Confirm",
      "giftWrap": "10.1",
      "misc": "3.2",
      "orderId": "testorderID",
      "promoCode": "testPromoCode",
      "reason": "Order Successfully Created",
      "shippingHandling": "5.1",
      "subtotal": "80.1",
      "tax": "7.1",
      "total": "101"
      }
   }'''
  end
  
  def test_updatePaymentInfo
    base_uri = 'wallet-services-web/'
    resource_path = 'payment/info/{callId}'
    resource_path = resource_path.sub('{callId}',@config['checkoutCallId'])
    api_key = @config['apiKey']
    response_code = @visa_api_client.doXPayTokenRequest(base_uri, resource_path, "apikey=#{api_key}", "Update Payment Data Test", "put", @updatePaymentInfoRequest)
    assert_equal("200", response_code, "Update Payment Information test failed")
    end
end
