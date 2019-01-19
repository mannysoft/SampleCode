require 'test/unit'
require 'rest-client'
require 'json'
require 'yaml'
require File.expand_path('../../../lib/visa_api_client', __FILE__)

class FundsTransferTest < Test::Unit::TestCase
  def setup
    @strDate = Time.now.strftime("%Y-%m-%dT%H:%M:%S")
    @visa_api_client = VisaAPIClient.new
    @pushFundsRequest ='''{
    "systemsTraceAuditNumber": 350420,
    "retrievalReferenceNumber": "401010350420",
    "localTransactionDateTime": "'''+ @strDate + '''",
    "acquiringBin": 409999,
    "acquirerCountryCode": "101",
    "senderAccountNumber": "1234567890123456",
    "senderCountryCode": "USA",
    "transactionCurrencyCode": "USD",
    "senderName": "John Smith",
    "senderAddress": "44 Market St.",
    "senderCity": "San Francisco",
    "senderStateCode": "CA",
    "recipientName": "Adam Smith",
    "recipientPrimaryAccountNumber": "4957030420210454",
    "amount": "112.00",
    "businessApplicationId": "AA",
    "transactionIdentifier": 234234322342343,
    "merchantCategoryCode": 6012,
    "sourceOfFundsCode": "03",
    "cardAcceptor": {
      "name": "John Smith",
      "terminalId": "13655392",
      "idCode": "VMT200911026070",
      "address": {
        "state": "CA",
        "county": "081",
        "country": "USA",
        "zipCode": "94105"
      }
    },
    "feeProgramIndicator": "123"
    }'''
  end
  
  def test_pushFunds
    base_uri = 'visadirect/'
    resource_path = 'fundstransfer/v1/pushfundstransactions'
    response_code = @visa_api_client.doMutualAuthRequest("#{base_uri}#{resource_path}", "Push Funds Transactions test", "post", @pushFundsRequest)
    assert_equal("200", response_code, "Push Funds Transactions test failed")
  end
end
