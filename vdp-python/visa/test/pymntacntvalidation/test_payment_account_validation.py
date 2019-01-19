from visa.helpers.visa_api_client import VisaAPIClient
import json
import unittest
'''
@author: visa
'''

class TestPaymentAccountValidation(unittest.TestCase):

    def setUp(self):
        self.visa_api_client = VisaAPIClient()
        self.payment_account_validation = json.loads('''{
          "acquirerCountryCode": "840",
          "acquiringBin": "408999",
          "addressVerificationResults": {
            "postalCode": "T4B 3G5",
            "street": "801 Metro Center Blv"
          },
          "cardAcceptor": {
            "address": {
              "city": "San Francisco",
              "country": "USA",
              "county": "CA",
              "state": "CA",
              "zipCode": "94404"
            },
            "idCode": "111111",
            "name": "rohan",
            "terminalId": "123"
          },
          "cardCvv2Value": "672",
          "cardExpiryDate": "2018-06",
          "primaryAccountNumber": "4957030000313108",
          "retrievalReferenceNumber": "015221743720",
          "systemsTraceAuditNumber": "743720"
        }''')
    
    def test_enrollement(self):
        base_uri = 'pav/'
        resource_path = 'v1/cardvalidation'
        response = self.visa_api_client.do_mutual_auth_request(base_uri + resource_path, self.payment_account_validation, 'Card Validation call', 'post')
        self.assertEqual(str(response.status_code) ,"200" ,"Card validation test failed")
        pass
