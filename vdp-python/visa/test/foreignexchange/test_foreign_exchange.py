from visa.helpers.visa_api_client import VisaAPIClient
import json
import unittest
'''
@author: visa
'''

class TestForeignExchange(unittest.TestCase):

    def setUp(self):
        self.visa_api_client = VisaAPIClient()
        self.foreign_exchange_request = json.loads('''{
          "acquirerCountryCode": "840",
          "acquiringBin": "408999",
          "cardAcceptor": {
            "address": {
              "city": "San Francisco",
              "country": "USA",
              "county": "San Mateo",
              "state": "CA",
              "zipCode": "94404"
            },
            "idCode": "ABCD1234ABCD123",
            "name": "ABCD",
            "terminalId": "ABCD1234"
          },
          "destinationCurrencyCode": "826",
          "markUpRate": "1",
          "retrievalReferenceNumber": "201010101031",
          "sourceAmount": "100.00",
          "sourceCurrencyCode": "840",
          "systemsTraceAuditNumber": "350421"
        }''')
    
    def test_foreign_exchange(self):
        base_uri = 'forexrates/'
        resource_path = 'v1/foreignexchangerates'
        response = self.visa_api_client.do_mutual_auth_request(base_uri + resource_path, self.foreign_exchange_request, 'Foreign Exchange call', 'post')
        self.assertEqual(str(response.status_code) ,"200" ,"Foreign exchange test failed")
        pass
