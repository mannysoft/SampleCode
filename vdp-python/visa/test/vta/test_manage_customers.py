from visa.helpers.visa_api_client import VisaAPIClient
import json
import datetime
import unittest
import sys
import os
if sys.version_info < (3, 0):
    import ConfigParser as parser
else:
    import configparser as parser
'''
@author: visa
'''

class TestManageCustomers(unittest.TestCase):
    
    config = parser.ConfigParser()
    config_path = os.path.abspath(os.path.join(os.path.dirname(os.path.dirname(__file__)),'..','configuration.ini'))
    config.read(config_path)
    
    def setUp(self):
        date = datetime.datetime.now().strftime("%Y-%m-%dT%H:%M:%S.%fZ")
        self.visa_api_client = VisaAPIClient()
        self.create_customer_request = json.loads('''{
    "customer": {
        "cards": [
            {
                "address": '''+ self.config.get('VDP','vtaCreateCustomerAddress') + ''',
                "billCycleDay": "7",
                "bin": 431263,
                "cardEnrollmentDate": "''' + date + '''",
                "cardExpiryDate": "''' + self.config.get('VDP','vtaCreateCustomerCardExpiryDate') + '''",
                "cardNickName": "My Card",
                "cardNumber": "''' + self.config.get('VDP','vtaCreateCustomerCardNumber') + '''",
                "cardSecurityCode": "''' + self.config.get('VDP','vtaCreateCustomerCardSecurityCode') + '''",
                "contactServiceOfferings": [
                    {
                        "contact": {
                            "contactNickName": "testEmail",
                            "contactType": "Email",
                            "contactValue": "john@visa.com",
                            "isVerified": true,
                            "lastUpdateDateTime": "''' + date + '''",
                            "mobileCountryCode": null,
                            "mobileVerificationCode": null,
                            "mobileVerificationCodeDate": "''' + datetime.datetime.now().strftime("%Y-%m-%dT%H:%M:%S") + '''",
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
                "lastFour": "''' + self.config.get('VDP','vtaCreateCustomerLastFour') + '''",
                "nameOnCard": "Migration",
                "paused": false,
                "portfolioNum": "''' + self.config.get('VDP','vtaPortfolioNumber') + '''",
                "previousCardNumber": null,
                "productId": null,
                "productIdDescription": "Credit",
                "productType": "Credit",
                "productTypeExtendedCode": "Credit",
                "rpin": null
            }
        ],
        "communityCode": "''' + self.config.get('VDP','vtaCommunityCode') + '''",
        "contacts": [
            {
                "contactNickName": "testEmail",
                "contactType": "Email",
                "contactValue": "john@visa.com",
                "isVerified": true,
                "lastUpdateDateTime": "''' + date + '''",
                "mobileCountryCode": null,
                "mobileVerificationCode": null,
                "mobileVerificationCodeDate": "''' + datetime.datetime.now().strftime("%Y-%m-%dT%H:%M:%S") + '''",
                "platform": "None",
                "preferredEmailFormat": "Html",
                "securityPhrase": null,
                "status": "Active"
            }
        ],
        "customerEnrollmentDate": "''' + date + '''",
        "customerId": "a1bb6fe1-ea64-4269-b29d-169aebd8780a",
        "firstName": "James",
        "isActive": ''' + self.config.get('VDP','vtaCreateCustomerIsActive') + ''',
        "lastName": "Bond",
        "preferredCountryCode": "''' + self.config.get('VDP','vtaCreateCustomerPreferedCountryCode') + '''",
        "preferredCurrencyCode": "''' + self.config.get('VDP','vtaCreateCustomerPreferedCurrencyCode') + '''",
        "preferredFuelAmount": "75",
        "preferredLanguage": "''' + self.config.get('VDP','vtaCreateCustomerPreferedLanguage') + '''",
        "preferredTimeZone": "''' + self.config.get('VDP','vtaCreateCustomerPreferedTimeZone') + '''",
        "preferredTipPercentage": "15"
    }
}''')
    
    def test_get_customers(self):
        base_uri = 'vta/'
        resource_path = 'v3/communities/'+ self.config.get('VDP','vtaCommunityCode') +"/customers";
        response = self.visa_api_client.do_mutual_auth_request(base_uri + resource_path, self.create_customer_request, 'Create Customers Test', 'post', {'ServiceId' : self.config.get('VDP','vtaServiceId')})
        self.assertEqual(str(response.status_code) ,"201" ,"Create Customers Test failed")
        pass
