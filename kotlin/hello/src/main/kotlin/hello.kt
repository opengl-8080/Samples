fun main(args: Array<String>) {
    val iterable: Iterable<Int> = listOf(1, 2, 1, 2, 2, 3, 1, 3)

    println(iterable.distinct())
}