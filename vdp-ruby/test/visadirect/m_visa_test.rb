require 'test/unit'
require 'rest-client'
require 'json'
require 'yaml'
require File.expand_path('../../../lib/visa_api_client', __FILE__)

class MVisaTest < Test::Unit::TestCase
  def setup
    @visa_api_client = VisaAPIClient.new
    @strDate = Time.now.utc.strftime("%Y-%m-%dT%H:%M:%S")
    @mVisaTransactionRequest ='''{
    "acquirerCountryCode": "643",
    "acquiringBin": "400171",
    "amount": "124.05",
    "businessApplicationId": "CI",
    "cardAcceptor": {
      "address": {
        "city": "Bangalore",
        "country": "IND"
      },
      "idCode": "ID-Code123",
      "name": "Card Accpector ABC"
    },
    "localTransactionDateTime": "''' + @strDate + '''",
    "merchantCategoryCode": "4829",
    "recipientPrimaryAccountNumber": "4123640062698797",
    "retrievalReferenceNumber": "430000367618",
    "senderAccountNumber": "4541237895236",
    "senderName": "Mohammed Qasim",
    "senderReference": "1234",
    "systemsTraceAuditNumber": "313042",
    "transactionCurrencyCode": "USD",
    "transactionIdentifier": "381228649430015"
  }'''
  end
  
  def test_mVisaTransacation
    base_uri = 'visadirect/'
    resource_path = 'mvisa/v1/cashinpushpayments'
    response_code = @visa_api_client.doMutualAuthRequest("#{base_uri}#{resource_path}", "M-Visa Transacation test", "post", @mVisaTransactionRequest)
    assert_equal("200", response_code, "M-Visa Transacation test failed")
  end
end
