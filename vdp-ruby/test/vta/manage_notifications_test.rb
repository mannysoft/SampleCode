require 'test/unit'
require 'rest-client'
require 'json'
require 'yaml'
require File.expand_path('../../../lib/visa_api_client', __FILE__)

class ManageNotificationsTest < Test::Unit::TestCase
  
  def setup
    @config = YAML.load_file('configuration.yml')
    @visa_api_client = VisaAPIClient.new
    @notificationSubscriptionRequest = '''{
      "contactType": "''' + @config['vtaNotificationContactType'] + '''",
      "contactValue": "john@visa.com",
      "emailFormat": "None",
      "last4": "''' + @config['vtaCreateCustomerLastFour'] + '''",
      "phoneCountryCode": "en-us",
      "platform": "None",
      "preferredLanguageCode": "''' + @config['vtaPreferredLanguageCode'] + '''",
      "serviceOffering": "WelcomeMessage",
      "serviceOfferingSubType": "WelcomeMessage",
      "substitutions": {}
    }'''
  end
  
  def test_notificationSubscription
    base_uri = 'vta/'
    resource_path = "v3/communities/#{@config['vtaCommunityCode']}/portfolios/#{@config['vtaPortfolioNumber']}/customers/#{@config['vtaCustomerId']}/notifications"
    response_code = @visa_api_client.doMutualAuthRequest("#{base_uri}#{resource_path}", "Notification Subscriptions Test", "post", @notificationSubscriptionRequest, {'ServiceId' => @config['vtaServiceId']})
    assert_equal("201", response_code, "Notification Subscriptions Test failed")
  end
end
