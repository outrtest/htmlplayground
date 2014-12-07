package htmlplayground

import java.net.URL
import org.jsoup.nodes.Document

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
    elements.foreach { element ⇒
      println(s"URL: $element")
      println(processElement(element.absUrl("href")))
      Thread.sleep(2000)
    }
  }

  case class Element(tag: String, description: String, attributes: Seq[Attribute])
  case class Attribute(name: String, deprecated: Boolean, documentation: String)

  def processAttributes(document: Document): Seq[Attribute] = {
    val attributes = document.select("dt").asScala
    val documentation = document.select("dd").asScala

    attributes.zip(documentation).map { case (attr, doc) ⇒
      val name = attr.select("a").asScala.headOption.map(_.text()).getOrElse("")

      // See https://developer.mozilla.org/en-US/docs/Web/HTML/Element/li
      val deprecated = attr.select("i.icon-thumbs-down-alt").size() != 0

      // See https://developer.mozilla.org/en-US/docs/Web/HTML/Element/a
      val obsolete = attr.select("span.obsolete").size() != 0

      Attribute(name, deprecated || obsolete, doc.text())
    }
  }

  /** TODO Preserve HTML in descriptions */
  def processElement(url: String): Element = {
    val document = Jsoup.parse(new URL(url), 5000)

    val tag = url.split('/').last

    // Read contents between first headline ("Summary") and second ("Attributes")
    val article = document.select("article")
    val headlines = article.select("h2")
    val (fst, snd) = (headlines.first(), headlines.get(1))
    val articleChildren = article.first().children()
    val descriptionElements = articleChildren.subList(
      fst.elementSiblingIndex() + 1,
      snd.elementSiblingIndex() - 1)

    val description = descriptionElements.iterator().asScala.map(_.text()).mkString("\n")
    Element(tag, description, processAttributes(document))
  }
}