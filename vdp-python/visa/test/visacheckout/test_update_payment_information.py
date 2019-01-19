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

class TestUpdatePaymentInformation(unittest.TestCase):
    
    config = parser.ConfigParser()
    config_path = os.path.abspath(os.path.join(os.path.dirname(os.path.dirname(__file__)),'..','configuration.ini'))
    config.read(config_path)
    
    def setUp(self):
        self.visa_api_client = VisaAPIClient()
        self.update_payment_info_request = json.loads('''{
                          "orderInfo": {
                          "currencyCode": "USD",
                          "discount": "5.25",
                          "eventType": "Confirm",
                          "giftWrap": "10.1",
                          "misc": "3.2",
                          "orderId": "testorderID",
                          "promoCode": "testPromoCode",
                          "reason": "Order Successfully Created",
                          "shippingHandling": "5.1",
                          "subtotal": "80.1",
                          "tax": "7.1",
                          "total": "101"
                        }
                     }''')

    def test_update_payment_info(self):
        base_uri = 'wallet-services-web/'
        resource_path = 'payment/info/{callId}'
        resource_path = resource_path.replace('{callId}', self.config.get('VDP','checkoutCallId'))
        query_string = 'apikey=' + self.config.get('VDP','apiKey')
        response = self.visa_api_client.do_x_pay_request(base_uri, resource_path , query_string, self.update_payment_info_request, 'Update Payment Information Test', 'put')
        self.assertEqual(str(response.status_code) ,"200" ,"Update Payment Information test failed")
        pass
