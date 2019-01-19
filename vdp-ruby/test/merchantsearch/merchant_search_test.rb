require 'test/unit'
require 'rest-client'
require 'json'
require 'yaml'
require File.expand_path('../../../lib/visa_api_client', __FILE__)

class MerchantSearchTest < Test::Unit::TestCase
  def setup
    @visa_api_client = VisaAPIClient.new
    @strDate = Time.now.strftime("%Y-%m-%dT%H:%M:%S.%L")
    @searchRequest ='''{
    "header": {
        "messageDateTime": "'''+ @strDate  +'''",
        "requestMessageId": "CDISI_GMR_001",
        "startIndex": "1"
    },
    "searchAttrList": {
      "visaMerchantId":"11687107",
      "visaStoreId":"125861096",
      "merchantName":"ALOHA CAFE",
      "merchantCountryCode":"840",
      "merchantCity": "LOS ANGELES",
      "merchantState": "CA",
      "merchantPostalCode": "90012",
      "merchantStreetAddress": "410 E 2ND ST", 
      "businessRegistrationId":"196007747",
      "acquirerCardAcceptorId":"191642760469222",            
      "acquiringBin":"486168"
     },
     "responseAttrList": [
      "GNSTANDARD"
     ],
     "searchOptions": {
        "maxRecords": "2",
        "matchIndicators": "true",
        "matchScore": "true"
     }
   }'''
  end
  
  def test_merchantSearch
    base_uri = 'merchantsearch/'
    resource_path = 'v1/search'
    response_code = @visa_api_client.doMutualAuthRequest("#{base_uri}#{resource_path}", "Merchant Search Test", "post", @searchRequest)
    assert_equal("200", response_code, "Merchant Search test failed")
  end
end
