import java.util.*

fun main(args: Array<String>) {
    val map = mapOf("one" to "ONE", "two" to "TWO", "three" to "THREE")
    val sortedMap: SortedMap<String, String> = map.toSortedMap()

    println(sortedMap)
}