from visa.helpers.visa_api_client import VisaAPIClient
import json
import datetime
import unittest
'''
@author: visa
'''

class TestATMLocator(unittest.TestCase):

    def setUp(self):
        date = datetime.datetime.now().strftime("%Y-%m-%dT%H:%M:%S.%fZ")
        self.visa_api_client = VisaAPIClient()
        self.atm_inquiry_request = json.loads('''{
          "requestData": {
                "culture": "en-US",
                "distance": "20",
                "distanceUnit": "mi",
                "location": {
                  "address": null,
                  "geocodes": null,
                  "placeName": "800 metro center , foster city,ca"
                },
                "metaDataOptions": 0,
                "options": {
                  "findFilters": [
                    {
                      "filterName": "PLACE_NAME",
                      "filterValue": "FORT FINANCIAL CREDIT UNION|ULTRON INC|U.S. BANK"
                    },
                    {
                      "filterName": "OPER_HRS",
                      "filterValue": "C"
                    },
                    {
                      "filterName": "AIRPORT_CD",
                      "filterValue": ""
                    },
                    {
                      "filterName": "WHEELCHAIR",
                      "filterValue": "N"
                    },
                    {
                      "filterName": "BRAILLE_AUDIO",
                      "filterValue": "N"
                    },
                    {
                      "filterName": "BALANCE_INQUIRY",
                      "filterValue": "N"
                    },
                    {
                      "filterName": "CHIP_CAPABLE",
                      "filterValue": "N"
                    },
                    {
                      "filterName": "PIN_CHANGE",
                      "filterValue": "N"
                    },
                    {
                      "filterName": "RESTRICTED",
                      "filterValue": "N"
                    },
                    {
                      "filterName": "PLUS_ALLIANCE_NO_SURCHARGE_FEE",
                      "filterValue": "N"
                    },
                    {
                      "filterName": "ACCEPTS_PLUS_SHARED_DEPOSIT",
                      "filterValue": "N"
                    },
                    {
                      "filterName": "V_PAY_CAPABLE",
                      "filterValue": "N"
                    },
                    {
                      "filterName": "READY_LINK",
                      "filterValue": "N"
                    }
                  ],
                  "operationName": "or",
                  "range": {
                    "count": 99,
                    "start": 0
                  },
                  "sort": {
                    "direction": "desc",
                    "primary": "city"
                  },
                  "useFirstAmbiguous": true
                }
              },
              "wsRequestHeaderV2": {
                "applicationId": "VATMLOC",
                "correlationId": "909420141104053819418",
                "requestMessageId": "test12345678",
                "requestTs": "''' + date + '''",
                "userBid": "10000108",
                "userId": "CDISIUserID"
              }
            }''')
    
    def test_locate_atm(self):
        base_uri = 'globalatmlocator/'
        resource_path = 'v1/localatms/atmsinquiry'
        response = self.visa_api_client.do_mutual_auth_request(base_uri + resource_path, self.atm_inquiry_request, 'ATM Locator test','post')
        self.assertEqual(str(response.status_code) ,"200" ,"ATM Locator Test failed")
        pass
