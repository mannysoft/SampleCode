from visa.helpers.visa_api_client import VisaAPIClient
import json
import datetime
import unittest
'''
@author: visa
'''

class TestMerchantSearchAPI(unittest.TestCase):

    def setUp(self):
        date = datetime.datetime.now().strftime("%Y-%m-%dT%H:%M:%S.%f")[:-3]
        self.visa_api_client = VisaAPIClient()
        self.locator_request = json.loads('''{
                         "header": {
                             "messageDateTime": "'''+ date  +'''",
                             "requestMessageId": "CDISI_GMR_001",
                             "startIndex": "1"
                         },
                      "searchAttrList": {
                         "visaMerchantId":"11687107",
                         "visaStoreId":"125861096",
                         "merchantName":"ALOHA CAFE",
                         "merchantCountryCode":"840",
                         "merchantCity": "LOS ANGELES",
                         "merchantState": "CA",
                         "merchantPostalCode": "90012",
                         "merchantStreetAddress": "410 E 2ND ST", 
                         "businessRegistrationId":"196007747",
                         "acquirerCardAcceptorId":"191642760469222",            
                         "acquiringBin":"486168"
                      },
                      "responseAttrList": [
                         "GNSTANDARD"
                      ],
                      "searchOptions": {
                         "maxRecords": "2",
                         "matchIndicators": "true",
                         "matchScore": "true"
                      }
                    }''')
    
    def test_merchant_search_API(self):
        base_uri = 'merchantsearch/'
        resource_path = 'v1/search'
        response = self.visa_api_client.do_mutual_auth_request(base_uri + resource_path, self.locator_request, 'Merchant Search Test', 'post')
        self.assertEqual(str(response.status_code) ,"200" ,"Merchant search test failed")
        pass
