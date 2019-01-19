require 'test/unit'
require 'rest-client'
require 'json'
require 'yaml'
require File.expand_path('../../../lib/visa_api_client', __FILE__)

class LocationUpdateTest < Test::Unit::TestCase
  def setup
    @visa_api_client = VisaAPIClient.new
    @strDate = Time.now.utc.strftime("%Y-%m-%dT%H:%M:%S.%LZ")
    @config = YAML.load_file('configuration.yml')
    @locationsRequestBody ='''{
    "accuracy": "5000",
    "cloudNotificationKey": "03e3ae03-a627-4241-bad6-58f811c18e46",
    "cloudNotificationProvider": "1",
    "deviceId": "'''+ @config['mlcDeviceId'] + '''",
    "deviceLocationDateTime": "'''+ @strDate + '''",
    "geoLocationCoordinate": {
      "latitude": "37.558546",
      "longitude": "-122.271079"
    },
    "header": {
      "messageDateTime": "'''+ @strDate + '''",
      "messageId": "'''+ @config['mlcMessageId'] + '''"
    },
    "issuerId": "'''+ @config['mlcIssuerId'] + '''",
    "provider": "1",
    "source": "2"
  }'''
  end
  
  def test_locationUpdate
    base_uri = 'mlc/'
    resource_path = 'locationupdate/v1/locations'
    response_code = @visa_api_client.doMutualAuthRequest("#{base_uri}#{resource_path}", "Location Update test", "post", @locationsRequestBody)
    assert_equal("200", response_code, "Location Update test failed")
  end
end
