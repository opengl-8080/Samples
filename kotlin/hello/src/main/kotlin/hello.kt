import java.util.*

fun main(args: Array<String>) {
    val list = listOf(1, 2, 2, 3, 3, 3)

    println(list.binarySearch(3))
    println(list.binarySearch(9))
    println(list.binarySearch(element = 3, fromIndex = 0, toIndex = 2))
    println(list.binarySearch(element = 3, comparator = Comparator { a, b -> a - b }))
}