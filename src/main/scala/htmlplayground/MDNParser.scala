package htmlplayground

import java.net.URL

import org.hyperscala.Markup
import org.hyperscala.io.HTMLToScala
import org.powerscala.IO
import org.hyperscala.html._

/**
 * @author Matt Hicks <matt@outr.com>
 */
object MDNParser {
  Markup.UnsupportedAttributeException = false

  val XMLRegex = """<(.+)>""".r

  val elementsURL = "https://developer.mozilla.org/en-US/docs/Web/HTML/Element"

  def main(args: Array[String]): Unit = {
    val elementsSource = IO.copy(new URL(elementsURL))
    val html = HTMLToScala.toHTML(elementsSource, clean = true).asInstanceOf[tag.HTML]
    val widgeted = html.byClass[tag.Div]("widgeted").head
    val codes = widgeted.byTag[tag.Code]
    codes.foreach {
      case c => c.outputChildrenString.trim match {
        case XMLRegex(tagName) => processElement(tagName)
      }
    }
  }

  def processElement(tagName: String) = {
    val url = s"https://developer.mozilla.org/en-US/docs/Web/HTML/Element/$tagName"
    println(s"$tagName, URL: $url")
  }
}