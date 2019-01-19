package libs

import com.typesafe.config.ConfigFactory

/**
  * Created by Aravinda on 5/16/2017.
  */
object ConfigUtils {

  def load(filePath: Option[String] = None) = {

    filePath.fold(
      ifEmpty = throw new IllegalArgumentException("Invalid config file path"))(
      file => ConfigFactory.load(file))
  }
}
