import java.util.*

fun main(args: Array<String>) {
    val map = mapOf("one" to "ONE", "two" to "TWO", "three" to "THREE")

    val comparator = Comparator<Map.Entry<String, String>> { a, b -> a.key.compareTo(b.key) }

    println(map.maxWith(comparator))
    println(map.minWith(comparator))
}