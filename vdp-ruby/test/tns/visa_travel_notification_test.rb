require 'test/unit'
require 'rest-client'
require 'json'
require 'yaml'
require File.expand_path('../../../lib/visa_api_client', __FILE__)

class VisaTravelNotificationServiceTest < Test::Unit::TestCase
  def setup
    @visa_api_client = VisaAPIClient.new
    @config = YAML.load_file('configuration.yml')
    @departureDate = Time.now.strftime("%Y-%m-%d")
    @returnDate = (Time.now + (7*24*60*60)).strftime("%Y-%m-%d")
    @travelNotificationRequest ='''{
    "addTravelItinerary": {
    "returnDate": "''' + @returnDate + '''",
    "departureDate": "''' + @departureDate +'''",
    "destinations": [
                      {
                        "state": "CA",
                        "country": "840"
                      }
                    ],
    "primaryAccountNumbers": ''' + @config['tnsCardNumbers'] + ''',
    "userId": "Rajesh",
    "partnerBid": "''' + @config['tnsPartnerBid'] + '''"
    }
   }'''
  end
 
  def test_addTravelItenary
    base_uri = 'travelnotificationservice/'
    resource_path = 'v1/travelnotification/itinerary'
    response_code = @visa_api_client.doMutualAuthRequest("#{base_uri}#{resource_path}", "Add Travel Itenary Test", "post", @travelNotificationRequest)
    assert_equal("200", response_code, "Add Travel Itenary test failed")
  end
end
