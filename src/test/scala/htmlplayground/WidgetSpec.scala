package htmlplayground

import org.scalatest._

class WidgetSpec extends FlatSpec with Matchers {
  "a" should "generate HTML" in {
    HTML.a("Hello world").toHtml should be ("<a>Hello world</a>")
    HTML.a("Hello world").href("url").toHtml should be ("<a href=\"url\">Hello world</a>")
    HTML.a("Hello world").href("url").href("url2").toHtml should be ("<a href=\"url2\">Hello world</a>")
  }

  "p" should "generate HTML" in {
    HTML.p().toHtml should be ("<p/>")
    HTML.p(HTML.a("Hello world")).toHtml should be ("<p><a>Hello world</a></p>")
  }
}
