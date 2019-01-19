require 'test/unit'
require 'rest-client'
require 'json'
require 'yaml'
require File.expand_path('../../../lib/visa_api_client', __FILE__)

class ValidationAPITest < Test::Unit::TestCase
  def setup
    @visa_api_client = VisaAPIClient.new
  end
  
  def test_retrieveTransactionType
    base_uri = 'vctc/'
    resource_path = 'validation/v1/decisions/history'
    query_param = '?limit=1&page=1'
    response_code = @visa_api_client.doMutualAuthRequest("#{base_uri}#{resource_path}#{query_param}", "Retrieve List of Recent Decision Records test", "get", '')
    assert_equal("200", response_code, "Retrieve List of Recent Decision Records test failed")
  end
end
