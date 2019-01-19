require 'test/unit'
require 'json'
require File.expand_path('../../../lib/visa_api_client', __FILE__)

class LocateAtmTest < Test::Unit::TestCase
  def setup
    @strDate = Time.now.utc.strftime("%Y-%m-%dT%H:%M:%S.%3NZ")
    @visa_api_client = VisaAPIClient.new
    @atmInquiryRequest ='''{
    "requestData": {
        "culture": "en-US",
        "distance": "20",
        "distanceUnit": "mi",
        "location": {
          "address": null,
          "geocodes": null,
          "placeName": "800 metro center , foster city,ca"
        },
        "metaDataOptions": 0,
        "options": {
          "findFilters": [
            {
              "filterName": "PLACE_NAME",
              "filterValue": "FORT FINANCIAL CREDIT UNION|ULTRON INC|U.S. BANK"
            },
            {
              "filterName": "OPER_HRS",
              "filterValue": "C"
            },
            {
              "filterName": "AIRPORT_CD",
              "filterValue": ""
            },
            {
              "filterName": "WHEELCHAIR",
              "filterValue": "N"
            },
            {
              "filterName": "BRAILLE_AUDIO",
              "filterValue": "N"
            },
            {
              "filterName": "BALANCE_INQUIRY",
              "filterValue": "N"
            },
            {
              "filterName": "CHIP_CAPABLE",
              "filterValue": "N"
            },
            {
              "filterName": "PIN_CHANGE",
              "filterValue": "N"
            },
            {
              "filterName": "RESTRICTED",
              "filterValue": "N"
            },
            {
              "filterName": "PLUS_ALLIANCE_NO_SURCHARGE_FEE",
              "filterValue": "N"
            },
            {
              "filterName": "ACCEPTS_PLUS_SHARED_DEPOSIT",
              "filterValue": "N"
            },
            {
              "filterName": "V_PAY_CAPABLE",
              "filterValue": "N"
            },
            {
              "filterName": "READY_LINK",
              "filterValue": "N"
            }
          ],
          "operationName": "or",
          "range": {
            "count": 99,
            "start": 0
          },
          "sort": {
            "direction": "desc",
            "primary": "city"
          },
          "useFirstAmbiguous": true
        }
      },
      "wsRequestHeaderV2": {
        "applicationId": "VATMLOC",
        "correlationId": "909420141104053819418",
        "requestMessageId": "test12345678",
        "requestTs": "'''+ @strDate + '''",
        "userBid": "10000108",
        "userId": "CDISIUserID"
      }
    }'''
  end
  
  def test_locateATMs
    base_uri = 'globalatmlocator/'
    resource_path = 'v1/localatms/atmsinquiry'
    response_code = @visa_api_client.doMutualAuthRequest("#{base_uri}#{resource_path}", "Locate ATM test", "post", @atmInquiryRequest)
    assert_equal("200", response_code, "Locate ATM test failed")
  end
  
end
