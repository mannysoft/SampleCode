require 'test/unit'
require 'rest-client'
require 'json'
require 'yaml'
require File.expand_path('../../../lib/visa_api_client', __FILE__)

class ConsumerRulesTest < Test::Unit::TestCase
  def setup
    @visa_api_client = VisaAPIClient.new
    @config = YAML.load_file('configuration.yml')
    @cardRegisterData ='''{
      "primaryAccountNumber": '''+ @config['vctcTestPan'] + '''
    }'''
  end
  
  def test_registerACard
    base_uri = 'vctc/'
    resource_path = 'customerrules/v1/consumertransactioncontrols'
    response_code = @visa_api_client.doMutualAuthRequest("#{base_uri}#{resource_path}", "Register a Card test", "post", @cardRegisterData)
    assert_equal(true, (response_code == "200" || response_code == "201"), "Register a Card test failed")
  end
end
