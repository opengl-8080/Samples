fun main(args: Array<String>) {
    val iterable: Iterable<Int> = listOf(1, 2, 3)

    println(iterable.any { it < 4 })
    println(iterable.any { it < 0 })
}