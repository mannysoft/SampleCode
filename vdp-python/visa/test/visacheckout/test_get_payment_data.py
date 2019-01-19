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

class TestGetPaymentData(unittest.TestCase):
    
    config = parser.ConfigParser()
    config_path = os.path.abspath(os.path.join(os.path.dirname(os.path.dirname(__file__)),'..','configuration.ini'))
    config.read(config_path)
    
    def setUp(self):
        self.visa_api_client = VisaAPIClient()

    def test_get_payment_info(self):
        base_uri = 'wallet-services-web/'
        resource_path = 'payment/data/{callId}'
        resource_path = resource_path.replace('{callId}', self.config.get('VDP','checkoutCallId'))
        query_string = 'apikey=' + self.config.get('VDP','apiKey')
        response = self.visa_api_client.do_x_pay_request(base_uri, resource_path , query_string, '', 'Get Payments Information Test', 'get')
        self.assertEqual(str(response.status_code) ,"200" ,"Get Payments Information test failed")
        pass
