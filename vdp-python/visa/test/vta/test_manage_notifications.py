from visa.helpers.visa_api_client import VisaAPIClient
import json
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

class TestManageNotifications(unittest.TestCase):
   
    config = parser.ConfigParser()
    config_path = os.path.abspath(os.path.join(os.path.dirname(os.path.dirname(__file__)),'..','configuration.ini'))
    config.read(config_path)
    
    def setUp(self):
        self.visa_api_client = VisaAPIClient()
        self.notification_subscription_request = json.loads('''{
                         "contactType": "''' + self.config.get('VDP','vtaNotificationContactType') + '''",
                         "contactValue": "john@visa.com",
                         "emailFormat": "None",
                         "last4": "''' + self.config.get('VDP','vtaCreateCustomerLastFour') + '''",
                         "phoneCountryCode": "en-us",
                         "platform": "None",
                         "preferredLanguageCode": "''' + self.config.get('VDP','vtaPreferredLanguageCode') + '''",
                         "serviceOffering": "WelcomeMessage",
                         "serviceOfferingSubType": "WelcomeMessage",
                         "substitutions": {}
                      }''')
         
    def test_notification_subscription(self):
        base_uri = 'vta/'
        resource_path = 'v3/communities/'+ self.config.get('VDP','vtaCommunityCode') +'/portfolios/' + self.config.get('VDP','vtaPortfolioNumber') +'/customers/' + self.config.get('VDP','vtaCustomerId')+ '/notifications';
        response = self.visa_api_client.do_mutual_auth_request(base_uri + resource_path, self.notification_subscription_request, 'Notification Subscriptions Test', 'post', {'ServiceId' : self.config.get('VDP','vtaServiceId')})
        self.assertEqual(str(response.status_code) ,"201" ,"Notification Subscriptions Test failed")
        pass
