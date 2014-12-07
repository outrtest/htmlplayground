package htmlplayground

// TODO This file is going to be auto-generated. It only contains some
// manually created elements for illustration purposes.

object HTML {
  case class a(children: Widget*) extends JVMWidget {
    val tagName = "a"
    def href(value: String) = { attributes.set("href", value); this }
    def href = attributes.get("href")
  }

  case class p(children: Widget*) extends JVMWidget {
    val tagName = "p"
  }
}
