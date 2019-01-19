from visa.helpers.visa_api_client import VisaAPIClient
import unittest
'''
@author: visa
'''

class TestProgramAdministration(unittest.TestCase):
    
    def setUp(self):
        self.visa_api_client = VisaAPIClient()
    
    def test_retrieve_transaction(self):
        base_uri = 'vctc/'
        resource_path = 'programadmin/v1/configuration/transactiontypecontrols'
        response = self.visa_api_client.do_mutual_auth_request(base_uri + resource_path, '', 'Retrieve Transaction Type Control call', 'get')
        self.assertEqual(str(response.status_code) ,"200" ,"Retrieve transaction type control test failed")
        pass
