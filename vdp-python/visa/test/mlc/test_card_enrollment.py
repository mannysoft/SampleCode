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

class TestCardEnrollement(unittest.TestCase):

    config = parser.ConfigParser()
    config_path = os.path.abspath(os.path.join(os.path.dirname(os.path.dirname(__file__)),'..','configuration.ini'))
    config.read(config_path)
    
    def setUp(self):
        self.visa_api_client = VisaAPIClient()
        self.enrollement_data = json.loads('''{
                    "enrollmentMessageType": "enroll",
                    "enrollmentRequest": {
                        "cardholderMobileNumber": "0016504323000",
                        "clientMessageID": "''' + self.config.get('VDP','mlcClientMessageID') +'''",
                        "deviceId": "''' + self.config.get('VDP','mlcDeviceId') +'''",
                        "issuerId": "''' + self.config.get('VDP','mlcIssuerId') +'''",
                        "primaryAccountNumber": "''' + self.config.get('VDP','mlcPrimaryAccountNumber') +'''"
                    }
                }''')
    
    def test_enrollement(self):
        base_uri = 'mlc/'
        resource_path = 'enrollment/v1/enrollments'
        response = self.visa_api_client.do_mutual_auth_request(base_uri + resource_path, self.enrollement_data, 'MLC Card Enrollement Test', 'post')
        self.assertEqual(str(response.status_code) ,"200" ,"MLC enrollment's test failed")
        pass
