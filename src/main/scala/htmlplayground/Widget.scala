package htmlplayground

import scala.collection.mutable

trait Attributes {
  def get(key: String): String
  def set(key: String, value: String)
  def iterator: Iterator[(String, String)]
}

trait Widget {
  def toHtml: String
}

trait HTMLWidget extends Widget with GlobalAttributes {
  val tagName: String
  val children: Seq[Widget]
  val attributes: Attributes
  val styleAttributes: StyleAttributes
}

object HTMLHelpers {
  def escape(value: String): String = value.replaceAll("\"", "\\\"")
  def quote(value: String): String = "\"" + escape(value) + "\""
}

// TODO Have two different implementations depending on whether Scala.js or the JVM is used
trait ArrayAttributes extends Attributes {
  private[htmlplayground] val attributes = mutable.HashMap.empty[String, String]
  def get(key: String): String = attributes.getOrElse(key, "")
  def set(key: String, value: String) { attributes += (key → value) }
  def iterator: Iterator[(String, String)] = attributes.iterator
}

case class HTMLAttributes() extends ArrayAttributes {
  def toHtml: String =
    attributes
      .map { case (key, value) ⇒ s"$key=" + HTMLHelpers.quote(value) }
      .mkString(" ")
}

trait DOMAttributes extends Attributes {
  def get(key: String): String = ???
  def set(key: String, value: String) = ???
}

trait JVMWidget extends HTMLWidget {
  val attributes = HTMLAttributes()
  val styleAttributes = StyleAttributes()

  def toHtml = {
    val attrs = if (attributes.iterator.isEmpty) "" else s" ${attributes.toHtml}"
    if (children.isEmpty) s"<$tagName$attrs/>"
    else {
      val htmlChildren = children.map(_.toHtml).mkString
      s"<$tagName$attrs>$htmlChildren</$tagName>"
    }
  }
}

case class TextWidget(value: String) extends Widget {
  def toHtml = value
}

object Widget {
  implicit def StringToTextWidget(value: String): TextWidget = TextWidget(value)
}

trait GlobalAttributes { this: HTMLWidget ⇒
  // TODO Write parser for https://developer.mozilla.org/en-US/docs/Web/HTML/global_attributes
  def title = attributes.get("title")
  def title(value: String) = { attributes.set("title", value); this }

  def style(set: StyleAttributes ⇒ StyleAttributes) = { attributes.set("style", set(styleAttributes).toCSS); this }
}
