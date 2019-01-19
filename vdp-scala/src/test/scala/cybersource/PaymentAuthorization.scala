package cybersource

import libs.{ConfigUtils, HttpClientResponse, VisaApiClient}
import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.scalatest.junit.JUnitRunner

import scala.collection.immutable.HashMap

/**
  * Created by Aravinda on 5/16/2017.
  */
@RunWith(classOf[JUnitRunner])
class PaymentAuthorization extends FunSuite with BeforeAndAfter {

  test("Create a credit card authorization") {
    val config = ConfigUtils.load(Option("./config/app.conf"))
    val apiClient = new VisaApiClient()
    val reqBody =
      s"""{
            	"amount":"0",
            	"currency":"USD",
            	"payment":{
            		"cardNumber":"4111111111111111",
            		"cardExpirationMonth":"10",
            		"cardExpirationYear":"2018"
            	},
            	"billTo":{
            		"street1":"901 Metro Center Blvd",
            		"street2":"Folsom Street",
            		"city":"Foster City",
            		"country":"USA",
            		"state":"CA",
            		"postalCode":"94404",
            		"firstName":"userFirst",
            		"lastName":"userLast",
            		"email":"bill@cybs.com",
            		"buildingNumber":"24",
            		"district":"san mateo",
            		"company":"visa",
            		"ipAddress":"10.20.408.500",
            		"phoneNumber":"6508764564"
            	}
            }""".split("\n").map(_.replaceAll("\\s", "").trim).mkString("")

    val baseUri = "cybersource/"
    val resourcePath = "payments/v1/authorizations"
    val queryParams = "apikey=" + config.getString("app.apiKey")
    val response: HttpClientResponse = apiClient.doXPayRequest(baseUri, resourcePath, queryParams, reqBody, "POST", HashMap[String, String]())
    println("responseStatus ::" + response.responseStatus)
    println("responseBody ::" + response.body)
    assert(201 == response.responseStatus)
  }
}