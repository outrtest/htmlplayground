package htmlplayground

import java.net.URL
import scala.collection.JavaConverters._
import org.jsoup.Jsoup

/**
 * @author Matt Hicks <matt@outr.com>
 * @author Tim Nieradzik <tim@kognit.io>
 */
object MDNParser {
  val elementsURL = "https://developer.mozilla.org/en-US/docs/Web/HTML/Element"

  def main(args: Array[String]) {
    val doc = Jsoup.parse(new URL(elementsURL), 5000)
    val elements = doc.select("div.widgeted").select("li").select("a").asScala
    elements.foreach(element ⇒ processElement(element.absUrl("href")))
  }

  def processElement(url: String) {
    println(s"URL: $url")
  }
}