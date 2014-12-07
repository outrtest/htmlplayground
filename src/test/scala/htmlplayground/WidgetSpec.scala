package htmlplayground

import org.scalatest._

class WidgetSpec extends FlatSpec with Matchers {
  "a" should "generate HTML" in {
    HTML.a("Hello world").toHtml should be ("<a>Hello world</a>")
    HTML.a("Hello world").href("url").toHtml should be ("<a href=\"url\">Hello world</a>")
    HTML.a("Hello world").href("url").href("url2").toHtml should be ("<a href=\"url2\">Hello world</a>")

    HTML.a("Hello world")
      .style(_.color("green"))
      .toHtml should be ("<a style=\"color:green\">Hello world</a>")

    HTML.a("Hello world")
      .style(_.color("green"))
      .style(_.backgroundColor("blue"))
      .toHtml should be ("<a style=\"background-color:blue;color:green\">Hello world</a>")

    HTML.a("Hello world")
      .style(_.color("green").backgroundColor("blue"))
      .toHtml should be ("<a style=\"background-color:blue;color:green\">Hello world</a>")
  }

  "p" should "generate HTML" in {
    HTML.p().toHtml should be ("<p/>")
    HTML.p(HTML.a("Hello world")).toHtml should be ("<p><a>Hello world</a></p>")
  }
}
