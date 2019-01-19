require 'test/unit'
require 'rest-client'
require 'json'
require 'yaml'
require File.expand_path('../../../lib/visa_api_client', __FILE__)

class ReplaceCardsTest < Test::Unit::TestCase
  
  def setup
    @date = Time.now.strftime("%Y-%m-%dT%H:%M:%S.%LZ")
    @config = YAML.load_file('configuration.yml')
    @visa_api_client = VisaAPIClient.new
    @replaceCardsRequest = '''{
    "communityCode": "''' + @config['vtaCommunityCode'] + '''",
    "newCard": {
        "address": ''' + @config['vtaReplaceCardNewAddress'] + ''',
        "billCycleDay": "22",
        "bin": null,
        "cardEnrollmentDate": "2016-06-10T08:36:59+00:00",
        "cardExpiryDate": "''' + @config['vtaReplaceCardExpiryDate'] + '''",
        "cardNickName": "My Visa 3",
        "cardNumber": "''' + @config['vtaReplaceCardNumber'] + '''",
        "cardSecurityCode": "''' + @config['vtaReplaceCardSecurityCode'] + '''",
        "isActive": true,
        "lastFour": "''' + @config['vtaReplaceCardLastFour'] + '''",
        "nameOnCard": "Mradul",
        "paused": false,
        "portfolioNum": "''' + @config['vtaPortfolioNumber'] + '''",
        "previousCardNumber": null,
        "productId": null,
        "productIdDescription": "Credit",
        "productType": "Credit",
        "productTypeExtendedCode": "123",
        "rpin": null
    },
    "oldCard": {
        "address": ''' + @config['vtaCreateCustomerAddress'] + ''',
        "billCycleDay": "22",
        "bin": null,
        "cardEnrollmentDate": "2016-06-10T08:36:59+00:00",
        "cardExpiryDate": "''' + @config['vtaCreateCustomerCardExpiryDate'] + '''",
        "cardNickName": "My Visa 3",
        "cardNumber": "''' + @config['vtaCreateCustomerCardNumber'] + '''",
        "cardSecurityCode": "''' + @config['vtaCreateCustomerCardSecurityCode'] + '''",
    "isActive": true,
    "lastFour": "'''+ @config['vtaCreateCustomerLastFour'] + '''",
    "nameOnCard": "ddd",
    "paused": false,
    "portfolioNum": "''' + @config['vtaPortfolioNumber'] + '''",
    "previousCardNumber": null,
    "productId": null,
    "productIdDescription": "Credit",
    "productType": "Credit",
    "productTypeExtendedCode": "123",
    "rpin": null
  }
}'''
  end
  
  def test_replaceACard
    base_uri = 'vta/'
    resource_path = "v3/communities/#{@config['vtaCommunityCode']}/cards"
    response_code = @visa_api_client.doMutualAuthRequest("#{base_uri}#{resource_path}", "Replace a card test", "post", @replaceCardsRequest, {'ServiceId' => @config['vtaServiceId']})
    assert_equal("201", response_code, "Replace a card test failed")
  end
end
