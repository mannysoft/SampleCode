require 'test/unit'
require 'rest-client'
require 'json'
require 'yaml'
require File.expand_path('../../../lib/visa_api_client', __FILE__)

class GeneralAttributesInquiryTest < Test::Unit::TestCase
  def setup
    @visa_api_client = VisaAPIClient.new
    @generalAttributeInquiry ='''{
      "primaryAccountNumber": "4465390000029077"
    }'''
  end
  
  def test_fundsTransferInquiry
    base_uri = 'paai/'
    resource_path = 'generalattinq/v1/cardattributes/generalinquiry'
    response_code = @visa_api_client.doMutualAuthRequest("#{base_uri}#{resource_path}", "General Attributes Inquiry test", "post", @generalAttributeInquiry)
    assert_equal("200", response_code, "General Attributes Inquiry test failed")
  end
end
