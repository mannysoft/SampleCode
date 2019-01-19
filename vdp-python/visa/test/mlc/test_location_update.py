from visa.helpers.visa_api_client import VisaAPIClient
import json
import sys
import os
import datetime
import unittest
if sys.version_info < (3, 0):
    import ConfigParser as parser
else:
    import configparser as parser
'''
@author: visa
'''

class TestLocationUpdate(unittest.TestCase):
    
    config = parser.ConfigParser()
    config_path = os.path.abspath(os.path.join(os.path.dirname(os.path.dirname(__file__)),'..','configuration.ini'))
    config.read(config_path)
    
    def setUp(self):
        date = datetime.datetime.utcnow().strftime("%Y-%m-%dT%H:%M:%S.%fZ")
        self.visa_api_client = VisaAPIClient()
        self.location_update = json.loads('''{
                                    "accuracy": "5000",
                                    "cloudNotificationKey": "03e3ae03-a627-4241-bad6-58f811c18e46",
                                    "cloudNotificationProvider": "1",
                                    "deviceId": "''' + self.config.get('VDP','mlcDeviceId') +'''",
                                    "deviceLocationDateTime": "''' + date + '''",
                                    "geoLocationCoordinate": {
                                        "latitude": "37.558546",
                                        "longitude": "-122.271079"
                                    },
                                    "header": {
                                                "messageDateTime": "''' + date + '''",
                                                "messageId": "''' + self.config.get('VDP','mlcMessageId') +'''"
                                            },
                                    "issuerId": "''' + self.config.get('VDP','mlcIssuerId') +'''",
                                    "provider": "1",
                                    "source": "2"
                                }''')
    
    def test_location_update(self):
        base_uri = 'mlc/'
        resource_path = 'locationupdate/v1/locations' 
        response = self.visa_api_client.do_mutual_auth_request(base_uri + resource_path, self.location_update, 'Location Update Test', 'post')
        self.assertEqual(str(response.status_code) ,"200" ,"Location update test failed")
        pass
