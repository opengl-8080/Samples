package example

import kotlin.browser.document

fun main(args: Array<String>) {
    val el = document.createElement("div")
    el.appendChild(document.createTextNode("Hello Kotlin JS!!"))
    document.body!!.appendChild(el)
}