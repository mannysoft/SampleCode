from visa.helpers.visa_api_client import VisaAPIClient
import json
import datetime
import unittest
'''
@author: visa
'''

class TestFundsTransfer(unittest.TestCase):

    def setUp(self):
        date = datetime.datetime.now().strftime("%Y-%m-%dT%H:%M:%S")
        self.visa_api_client = VisaAPIClient()
        self.push_funds_request = json.loads('''{
            "systemsTraceAuditNumber": 350420,
            "retrievalReferenceNumber": "401010350420",
            "localTransactionDateTime": "'''+ date + '''",
            "acquiringBin": 408999,
            "acquirerCountryCode": "101",
            "senderAccountNumber": "4957030420210454",
            "transactionCurrencyCode": "USD",
            "senderName": "John Smith",
            "senderCountryCode": "USA",
            "senderAddress": "44 Market St.",
            "senderCity": "San Francisco",
            "senderStateCode": "CA",
            "recipientName": "Adam Smith",
            "recipientPrimaryAccountNumber": "4957030420210462",
            "amount": "350.00",
            "businessApplicationId": "AA",
            "transactionIdentifier": 234234322342343,
            "merchantCategoryCode": 6012,
            "sourceOfFundsCode": "03",
            "cardAcceptor": {
              "name": "Acceptor 1",
              "terminalId": "13655392",
              "idCode": "VMT200911026070",
              "address": {
                "state": "CA",
                "county": "081",
                "country": "USA",
                "zipCode": "94105"
              }
            },
            "feeProgramIndicator": "123"
        }''')
    
    def test_push_funds_transactions(self):
        base_uri = 'visadirect/'
        resource_path = 'fundstransfer/v1/pushfundstransactions'
        response = self.visa_api_client.do_mutual_auth_request(base_uri + resource_path, self.push_funds_request, 'Push Funds Transaction Test','post')
        self.assertEqual(str(response.status_code) ,"200" ,"Push Funds Transaction test failed")
        pass
