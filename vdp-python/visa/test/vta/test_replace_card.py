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

class TestReplaceCard(unittest.TestCase):
    
    config = parser.ConfigParser()
    config_path = os.path.abspath(os.path.join(os.path.dirname(os.path.dirname(__file__)),'..','configuration.ini'))
    config.read(config_path)
    
    def setUp(self):
        self.visa_api_client = VisaAPIClient()
        self.replace_cards_request = json.loads('''{
            "communityCode": "''' + self.config.get('VDP','vtaCommunityCode') + '''",
            "newCard": {
                "address": '''+ self.config.get('VDP','vtaReplaceCardNewAddress') + ''',
                "billCycleDay": "22",
                "bin": null,
                "cardEnrollmentDate": "2016-06-10T08:36:59+00:00",
                "cardExpiryDate": "''' + self.config.get('VDP','vtaReplaceCardExpiryDate') + '''",
                "cardNickName": "My Visa 3",
                "cardNumber": "''' + self.config.get('VDP','vtaReplaceCardNumber') + '''",
                "cardSecurityCode": "''' + self.config.get('VDP','vtaReplaceCardSecurityCode') + '''",
                "isActive": true,
                "lastFour": "''' + self.config.get('VDP','vtaReplaceCardLastFour') + '''",
                "nameOnCard": "Mradul",
                "paused": false,
                "portfolioNum": "''' + self.config.get('VDP','vtaPortfolioNumber') + '''",
                "previousCardNumber": null,
                "productId": null,
                "productIdDescription": "Credit",
                "productType": "Credit",
                "productTypeExtendedCode": "123",
                "rpin": null
            },
            "oldCard": {
                "address": '''+ self.config.get('VDP','vtaCreateCustomerAddress') + ''',
                "billCycleDay": "22",
                "bin": null,
                "cardEnrollmentDate": "2016-06-10T08:36:59+00:00",
                "cardExpiryDate": "''' + self.config.get('VDP','vtaCreateCustomerCardExpiryDate') + '''",
                "cardNickName": "My Visa 3",
                "cardNumber": "''' + self.config.get('VDP','vtaCreateCustomerCardNumber')+ '''",
                "cardSecurityCode": "''' + self.config.get('VDP','vtaCreateCustomerCardSecurityCode') + '''",
            "isActive": true,
            "lastFour": "'''+ self.config.get('VDP','vtaCreateCustomerLastFour') + '''",
            "nameOnCard": "Mradul",
            "paused": false,
            "portfolioNum": "''' + self.config.get('VDP','vtaPortfolioNumber') + '''",
            "previousCardNumber": null,
            "productId": null,
            "productIdDescription": "Credit",
            "productType": "Credit",
            "productTypeExtendedCode": "123",
            "rpin": null
          }
}''')
    
    def test_get_communities(self):
        base_uri = 'vta/'
        resource_path = 'v3/communities/' +  self.config.get('VDP','vtaCommunityCode') + '/cards'
        response = self.visa_api_client.do_mutual_auth_request(base_uri + resource_path, self.replace_cards_request, 'Replace a card test', 'post', {'ServiceId' : self.config.get('VDP','vtaServiceId')})
        self.assertEqual(str(response.status_code) ,"201" ,"Replace a card test failed")
        pass
