from visa.helpers.visa_api_client import VisaAPIClient
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

class TestManagePortfolios(unittest.TestCase):
    
    config = parser.ConfigParser()
    config_path = os.path.abspath(os.path.join(os.path.dirname(os.path.dirname(__file__)),'..','configuration.ini'))
    config.read(config_path)
    
    def setUp(self):
        self.visa_api_client = VisaAPIClient()
    
    def test_portfolios(self):
        base_uri = 'vta/'
        resource_path = 'v3/communities/'+ self.config.get('VDP','vtaCommunityCode') +'/portfolios'
        response = self.visa_api_client.do_mutual_auth_request(base_uri + resource_path, '', 'Get Portfolio Details Test', 'get', {'ServiceId' : self.config.get('VDP','vtaServiceId')})
        self.assertEqual(str(response.status_code) ,"200" ,"Get Portfolio Details Test failed")
        pass
