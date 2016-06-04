import java.util.*

fun main(args: Array<String>) {
    val map = mapOf("one" to "ONE", "two" to "TWO", "three" to "THREE")
    val properties: Properties = map.toProperties()

    println(properties)
}