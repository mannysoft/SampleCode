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

class TestVisaTravelNotificationService(unittest.TestCase):

    config = parser.ConfigParser()
    config_path = os.path.abspath(os.path.join(os.path.dirname(os.path.dirname(__file__)),'..','configuration.ini'))
    config.read(config_path)
    
    def setUp(self):
        departureDate = datetime.datetime.now().strftime("%Y-%m-%d")
        returnDate = datetime.datetime.strptime(departureDate, "%Y-%m-%d") + datetime.timedelta(days=10)
        self.visa_api_client = VisaAPIClient()
        self.travel_notification_request = json.loads('''{
            "addTravelItinerary": {
            "returnDate": "''' + returnDate.strftime("%Y-%m-%d") + '''",
            "departureDate": "''' + departureDate +'''",
            "destinations": [
                              {
                                "state": "CA",
                                "country": "840"
                              }
                            ],
            "primaryAccountNumbers": ''' + self.config.get('VDP','tnsCardNumbers') + ''',
            "userId": "Rajesh",
            "partnerBid": "''' + self.config.get('VDP','tnsPartnerBid') + '''"
            }
        }''')
    
    def test_add_travel_itenary(self):
        base_uri = 'travelnotificationservice/'
        resource_path = 'v1/travelnotification/itinerary'
        response = self.visa_api_client.do_mutual_auth_request(base_uri + resource_path, self.travel_notification_request, 'Add Travel Itenary Test','post')
        self.assertEqual(str(response.status_code) ,"200" ,"Add travel itenary test failed")
        pass
