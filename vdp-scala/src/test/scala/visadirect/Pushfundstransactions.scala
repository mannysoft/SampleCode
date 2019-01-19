package visadirect

import libs.{HttpClientResponse, VisaApiClient}
import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatest.junit.JUnitRunner

import scala.collection.immutable.HashMap

/**
  * Created by Aravinda on 5/16/2017.
  */
@RunWith(classOf[JUnitRunner])
class Pushfundstransactions extends FunSuite with BeforeAndAfter {

  test("Visa Direct Push Funds Transactions test") {

    val apiClient = new VisaApiClient()
    val baseUri = "visadirect/"
    val resourcePath = "fundstransfer/v1/pushfundstransactions"
    val formatter = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val strDate = formatter.format(new java.util.Date())
    val reqBody =
      s"""{
           "systemsTraceAuditNumber":451001,
           "retrievalReferenceNumber":"330000550000",
           "localTransactionDateTime":"$strDate",
           "acquiringBin":408999 ,
           "acquirerCountryCode":"840",
           "senderAccountNumber":"495703042020470",
           "senderCountryCode":"USA",
           "transactionCurrencyCode":"CAD",
           "senderName":"John Smith",
           "senderAddress":"44 Market St.",
           "senderCity":"San Francisco",
           "senderStateCode":"CA",
           "recipientName":"Adam Smith",
           "recipientPrimaryAccountNumber":"4957030420210454",
           "amount":"200.00",
           "businessApplicationId":"AA",
           "transactionIdentifier":234234322342343,
           "merchantCategoryCode":6012,
           "sourceOfFundsCode":"03",
           "cardAcceptor":{
             "name":"John Smith",
             "terminalId":"13655392",
             "idCode":"VMT200911026070",
             "address":{
                 "state":"AB",
                 "county":"081",
                 "country":"CAN",
                 "zipCode":"94105"
             }
           },
           "feeProgramIndicator":"123"
      }""".stripMargin

    val response: HttpClientResponse = apiClient.doMutualAuthRequest(baseUri, resourcePath, reqBody, "POST", HashMap[String, String]())
    println("responseStatus ::" + response.responseStatus)
    println("responseBody ::" + response.body)
    assert(200 == response.responseStatus)
  }

}
