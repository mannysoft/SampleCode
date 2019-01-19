package libs

import java.io.FileInputStream
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import java.util.Base64

import org.apache.http.HttpHeaders
import org.apache.http.client.methods._
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.entity.{ContentType, StringEntity}
import org.apache.http.impl.client.{CloseableHttpClient, HttpClients}
import org.apache.http.ssl.SSLContexts

import scala.collection.immutable.HashMap
import scala.util.Random


/**
  * Created by Aravinda on 5/16/2017.
  */
class VisaApiClient {

  val config = ConfigUtils.load(Option("./config/app.conf"))

  def doXPayRequest(baseUri: String, resourcePath: String, queryParams: String, requestBody: String,
                    methodType: String, headers: HashMap[String, String]) = {

    val headersMap = HashMap(
      HttpHeaders.CONTENT_TYPE -> "application/json",
      HttpHeaders.ACCEPT -> "application/json",
      "x-pay-token" -> XPayUtils.generateXpaytoken(resourcePath, queryParams, requestBody),
      "ex-correlation-id" -> s"${Random.alphanumeric.take(10).mkString}_SC"
    )
    headersMap.merged(headers)({ case ((k, v1), (_, v2)) => (k, v1) })

    val url = s"${config.getString("app.visaUrl")}$baseUri$resourcePath?$queryParams"
    val req = createMethod(methodType, url)
    attachBody(req, requestBody)
    attachHeaders(req, headersMap)
    val client: CloseableHttpClient = simpleHttpClient
    HttpClientResponse(client.execute(req))
  }

  def doMutualAuthRequest(baseUri: String, resourcePath: String, requestBody: String,
                          methodType: String, headers: HashMap[String, String]) = {

    val headersMap = HashMap(
      HttpHeaders.CONTENT_TYPE -> "application/json",
      HttpHeaders.ACCEPT -> "application/json",
      HttpHeaders.AUTHORIZATION -> getBasicAuthHeader(
        config.getString("app.userId"),
        config.getString("app.password")
      ),
      "ex-correlation-id" -> s"${Random.alphanumeric.take(10).mkString}_SC"
    )
    headersMap.merged(headers)({ case ((k, v1), (_, v2)) => (k, v1) })

    val url = s"${config.getString("app.visaUrl")}$baseUri$resourcePath"
    val req = createMethod(methodType, url)
    attachBody(req, requestBody)
    attachHeaders(req, headersMap)
    val client: CloseableHttpClient = sslHttpClient(config.getString("app.keyStorePath"),
      config.getString("app.keyStorePassword"))
    HttpClientResponse(client.execute(req))
  }

  private def getBasicAuthHeader(userId: String, password: String): String = {
    "Basic " + Base64.getEncoder.encodeToString((userId + ":" + password).getBytes(StandardCharsets.UTF_8))
  }

  private def simpleHttpClient = HttpClients.createDefault

  private def sslHttpClient(p12Path: String, p12Password: String) = {
    val keyStore = KeyStore.getInstance("JKS")
    keyStore.load(new FileInputStream(p12Path), p12Password.toArray)
    val context = SSLContexts.custom().loadKeyMaterial(keyStore, p12Password.toCharArray)
      .build()
    val sslSocketFactory = new SSLConnectionSocketFactory(context, Array("TLSv1"), null,
      SSLConnectionSocketFactory.getDefaultHostnameVerifier)
    HttpClients.custom().setSSLSocketFactory(sslSocketFactory).build
  }

  private def attachHeaders(req: HttpRequestBase, headers: Map[String, String]) {
    headers.foreach { case (name, value) => req.setHeader(name, value) }
  }

  private def createMethod(method: String, url: String) = {
    val req = method.toUpperCase match {
      case "GET" => new HttpGet(url)
      case "HEAD" => new HttpHead(url)
      case "OPTIONS" => new HttpOptions(url)
      case "DELETE" => new HttpDelete(url)
      case "TRACE" => new HttpTrace(url)
      case "POST" => new HttpPost(url)
      case "PUT" => new HttpPut(url)
      case "PATCH" => new HttpPatch(url)
    }
    req
  }

  private def attachBody(req: HttpRequestBase, jsonString: String) {
    req match {
      case r: HttpEntityEnclosingRequestBase =>
        val requestEntity = new StringEntity(
          jsonString,
          ContentType.APPLICATION_JSON)
        r.setEntity(requestEntity)
      case _ =>
        throw new IllegalArgumentException(
          """|HTTP %s does not support enclosing an entity.
            |Please remove the value from `body` parameter
            |or use POST/PUT/PATCH instead.""".stripMargin.format(req.getMethod))
    }
  }

}


