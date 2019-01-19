require 'test/unit'
require 'rest-client'
require 'json'
require 'yaml'
require File.expand_path('../../../lib/visa_api_client', __FILE__)

class ManageCustomersTest < Test::Unit::TestCase
  
  def setup
    @config = YAML.load_file('configuration.yml')
    @visa_api_client = VisaAPIClient.new
    @date = Time.now.strftime("%Y-%m-%dT%H:%M:%S.%LZ")
    @createCustomersRequest = '''{
    "customer": {
        "cards": [
            {
                "address": ''' + @config['vtaCreateCustomerAddress'] + ''',
                "billCycleDay": "7",
                "bin": 431263,
                "cardEnrollmentDate": "''' + @date + '''",
                "cardExpiryDate": "''' + @config['vtaCreateCustomerCardExpiryDate'] + '''",
                "cardNickName": "My Card",
                "cardNumber": "''' + @config['vtaCreateCustomerCardNumber'] + '''",
                "cardSecurityCode": "''' + @config['vtaCreateCustomerCardSecurityCode'] + '''",
                "contactServiceOfferings": [
                    {
                        "contact": {
                            "contactNickName": "testEmail",
                            "contactType": "Email",
                            "contactValue": "john@visa.com",
                            "isVerified": true,
                            "lastUpdateDateTime": "''' + @date + '''",
                            "mobileCountryCode": null,
                            "mobileVerificationCode": null,
                            "mobileVerificationCodeDate": "''' + Time.now.strftime("%Y-%m-%dT%H:%M:%S")+ '''",
                            "platform": "None",
                            "preferredEmailFormat": "Html",
                            "securityPhrase": null,
                            "status": "Active"
                        },
                        "serviceOfferings": [
                            {
                                "isActive": true,
                                "offeringId": "Threshold",
                                "offeringProperties": [
                                    {
                                        "key": "ThresholdAmount",
                                        "value": "10"
                                    }
                                ]
                            },
                            {
                                "isActive": true,
                                "offeringId": "CrossBorder",
                                "offeringProperties": [
                                    {
                                        "key": "ThresholdAmount",
                                        "value": "10"
                                    }
                                ]
                            },
                            {
                                "isActive": true,
                                "offeringId": "Declined",
                                "offeringProperties": [
                                    {
                                        "key": "ThresholdAmount",
                                        "value": "10"
                                    }
                                ]
                            },
                            {
                                "isActive": true,
                                "offeringId": "CardNotPresent",
                                "offeringProperties": [
                                    {
                                        "key": "ThresholdAmount",
                                        "value": "10"
                                    }
                                ]
                            }
                        ]
                    }
                ],
                "isActive": true,
                "lastFour": "''' + @config['vtaCreateCustomerLastFour'] + '''",
                "nameOnCard": "Migration",
                "paused": false,
                "portfolioNum": "''' + @config['vtaPortfolioNumber'] + '''",
                "previousCardNumber": null,
                "productId": null,
                "productIdDescription": "Credit",
                "productType": "Credit",
                "productTypeExtendedCode": "Credit",
                "rpin": null
            }
        ],
        "communityCode": "''' + @config['vtaCommunityCode'] + '''",
        "contacts": [
            {
                "contactNickName": "testEmail",
                "contactType": "Email",
                "contactValue": "john@visa.com",
                "isVerified": true,
                "lastUpdateDateTime": "''' + @date + '''",
                "mobileCountryCode": null,
                "mobileVerificationCode": null,
                "mobileVerificationCodeDate": "''' + Time.now.strftime("%Y-%m-%dT%H:%M:%S") + '''",
                "platform": "None",
                "preferredEmailFormat": "Html",
                "securityPhrase": null,
                "status": "Active"
            }
        ],
        "customerEnrollmentDate": "''' + @date + '''",
        "customerId": "a1bb6fe1-ea64-4269-b29d-169aebd8780a",
        "firstName": "James",
        "isActive": ''' + @config['vtaCreateCustomerIsActive'] + ''',
        "lastName": "Bond",
        "preferredCountryCode": "''' + @config['vtaCreateCustomerPreferedCountryCode'] + '''",
        "preferredCurrencyCode": "''' + @config['vtaCreateCustomerPreferedCurrencyCode'] + '''",
        "preferredFuelAmount": "75",
        "preferredLanguage": "''' + @config['vtaCreateCustomerPreferedLanguage'] + '''",
        "preferredTimeZone": "''' + @config['vtaCreateCustomerPreferedTimeZone'] + '''",
        "preferredTipPercentage": "15"
    }
}
    '''
  end
  
  def test_getCustomers
    base_uri = 'vta/'
    resource_path = "v3/communities/#{@config['vtaCommunityCode']}/customers"
    response_code = @visa_api_client.doMutualAuthRequest("#{base_uri}#{resource_path}", "Create Customers Request", "post", @createCustomersRequest, {'ServiceId' => @config['vtaServiceId']})
    assert_equal("201", response_code, "Create Customers test failed")
  end
end
