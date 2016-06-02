import java.util.*

fun main(args: Array<String>) {
    val iterable = listOf(3, 1, 2, 3, 1, 4)

    val result: SortedSet<Int> = iterable.toSortedSet()

    println(result)
}