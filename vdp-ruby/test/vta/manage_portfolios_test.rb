require 'test/unit'
require 'rest-client'
require 'json'
require 'yaml'
require File.expand_path('../../../lib/visa_api_client', __FILE__)

class ManagePortfoliosTest < Test::Unit::TestCase
  
  def setup
    @config = YAML.load_file('configuration.yml')
    @visa_api_client = VisaAPIClient.new
  end
  
  def test_portfolios
    base_uri = 'vta/'
    resource_path = "v3/communities/#{@config['vtaCommunityCode']}/portfolios"
    response_code = @visa_api_client.doMutualAuthRequest("#{base_uri}#{resource_path}", "Get Portfolio Details Test", "get", '', {'ServiceId' => @config['vtaServiceId']})
    assert_equal("200", response_code, "Get Portfolio Details test failed")
  end
end
