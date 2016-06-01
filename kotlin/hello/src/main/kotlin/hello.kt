import java.util.*

fun main(args: Array<String>) {
    val iterable = listOf(3, 1, 2, 4)

    println(iterable.sortedWith(Comparator { left, right -> right - left }))
}