package libs

import java.io.ByteArrayOutputStream

import org.apache.http.HttpResponse

/**
  * Created by Aravinda on 5/16/2017.
  */
case class HttpClientResponse(res: HttpResponse) {

  def body = {
    val entity = res.getEntity
    var content = ""
    if (entity != null) {
      val inputStream = entity.getContent
      content = scala.io.Source.fromInputStream(inputStream).mkString
    }
    println(content)
    content
  }

  val responseStatus = res.getStatusLine.getStatusCode

  def headers = {
    res.getAllHeaders.foldLeft(Map[String, Seq[String]]()) { (hmap, header) =>
      val Array(name, value) = Array(header.getName, header.getValue)
      val values = hmap.getOrElse(name, Seq())
      hmap + (name -> (values :+ value))
    }
  }

  def getBytes(res: HttpResponse) = {
    val bos = new ByteArrayOutputStream()
    res.getEntity.writeTo(bos)

    bos.toByteArray
  }
}
