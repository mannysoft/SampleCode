from visa.helpers.visa_api_client import VisaAPIClient
import json
import sys
import os
import unittest
if sys.version_info < (3, 0):
    import ConfigParser as parser
else:
    import configparser as parser
'''
@author: visa
'''

class TestConsumerRules(unittest.TestCase):

    config = parser.ConfigParser()
    config_path = os.path.abspath(os.path.join(os.path.dirname(os.path.dirname(__file__)),'..','configuration.ini'))
    config.read(config_path)
    
    def setUp(self):
        self.visa_api_client = VisaAPIClient()
        self.card_register_data = json.loads('''{
          "primaryAccountNumber": ''' + self.config.get('VDP','vctcTestPan') +'''
        }''')
    
    def test_register_a_card(self):
        base_uri = 'vctc/'
        resource_path = 'customerrules/v1/consumertransactioncontrols'
        response = self.visa_api_client.do_mutual_auth_request(base_uri + resource_path, self.card_register_data, 'Register a card call', 'post')
        self.assertTrue((str(response.status_code) == "200" or str(response.status_code) == "201"),"Register a card test failed")
        pass
