from visa.helpers.visa_api_client import VisaAPIClient
import json
import datetime
import unittest
'''
@author: visa
'''

class TestMerchantLocatorAPI(unittest.TestCase):

    def setUp(self):
        date = datetime.datetime.now().strftime("%Y-%m-%dT%H:%M:%S.%f")[:-3]
        self.visa_api_client = VisaAPIClient()
        self.locator_request = json.loads('''{
                "header": {
                    "messageDateTime": "''' + date + '''",
                    "requestMessageId": "VCO_GMR_001"
                },
                "searchAttrList": {
                    "merchantName": "ALOHA CAFE",
                    "merchantCountryCode": "840",
                    "latitude": "34.047616",
                    "longitude": "-118.239079",
                    "distance": "100",
                    "distanceUnit": "M"
                },
                "responseAttrList": [
                "GNLOCATOR"
                ],
                "searchOptions": {
                    "maxRecords": "2",
                    "matchIndicators": "true",
                    "matchScore": "true"
                }
            }''')
    
    def test_merchant_locator_API(self):
        base_uri = 'merchantlocator/'
        resource_path = 'v1/locator'
        response = self.visa_api_client.do_mutual_auth_request(base_uri + resource_path, self.locator_request, 'Merchant Locator Test', 'post')
        self.assertEqual(str(response.status_code) ,"200" ,"Merchant locator test failed")
        pass
