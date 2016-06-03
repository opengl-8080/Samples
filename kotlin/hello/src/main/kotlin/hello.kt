import java.util.*

fun main(args: Array<String>) {
    val mutableList = mutableListOf(2, 3, 1)

    mutableList.sortWith(Comparator { a, b ->  b - a})
    println(mutableList)
}