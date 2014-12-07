package htmlplayground

case class CSSAttributes() extends ArrayAttributes {
  def toCSS: String =
    attributes
      .map { case (key, value) ⇒ s"$key:$value" }
      .mkString(";")
}

case class StyleAttributes() {
  val attributes = CSSAttributes()

  // TODO Write parser for CSS specification
  // TODO Take Color as argument
  def backgroundColor(value: String) = { attributes.set("background-color", value); this }
  def backgroundColor = attributes.get("background-color")

  def color(value: String) = { attributes.set("color", value); this }
  def color = attributes.get("color")

  def toCSS: String = attributes.toCSS
}

