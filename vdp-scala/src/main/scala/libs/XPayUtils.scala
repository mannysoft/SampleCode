package libs

import java.nio.charset.StandardCharsets
import java.security.NoSuchAlgorithmException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

import org.apache.commons.codec.binary.Hex

/**
  * Created by Aravinda on 5/16/2017.
  */
object XPayUtils {

  private val config = ConfigUtils.load(Option("./config/app.conf"))

  def generateXpaytoken(resourcePath: String, queryString: String, requestBody: String) = {
    val timestamp: Long = timeStamp
    val beforeHash: String = timestamp + resourcePath + queryString + requestBody
    val hash: String = hmacSha256Digest(beforeHash)
    val token: String = "xv2:" + timestamp + ":" + hash
    println(token)
    token
  }

  private def timeStamp = System.currentTimeMillis / 1000L

  private def hmacSha256Digest(data: String) = getDigest("HmacSHA256", config.getString("app.sharedSecret"), data)

  private def getDigest(algorithm: String, sharedSecret: String, data: String): String = try {
    val sha256HMAC = Mac.getInstance(algorithm)
    val secretKey = new SecretKeySpec(sharedSecret.getBytes(StandardCharsets.UTF_8), algorithm)
    sha256HMAC.init(secretKey)
    val hashByte: Array[Byte] = sha256HMAC.doFinal(data.getBytes(StandardCharsets.UTF_8))
    Hex.encodeHexString(hashByte).toLowerCase
  }catch {
    case e: NoSuchAlgorithmException => println(s"Invalid MAC algoritham :: $algorithm"); e.toString
    case e: IllegalStateException => println(e.getMessage); e.toString
  }
}
