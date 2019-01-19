require 'test/unit'
require 'rest-client'
require 'json'
require 'yaml'
require File.expand_path('../../../lib/visa_api_client', __FILE__)

class ProgramAdministrationTest < Test::Unit::TestCase
  def setup
    @visa_api_client = VisaAPIClient.new
  end
  
  def test_retrieveTransactionType
    base_uri = 'vctc/'
    resource_path = 'programadmin/v1/configuration/transactiontypecontrols'
    response_code = @visa_api_client.doMutualAuthRequest("#{base_uri}#{resource_path}", "Retrieve Transaction Type Control test", "get", '')
    assert_equal("200", response_code, "Retrieve Transaction Type Control test failed")
  end
end
