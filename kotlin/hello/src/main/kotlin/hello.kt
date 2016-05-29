fun main(args: Array<String>) {
    val iterable: Iterable<Int> = listOf(1, 2, 3, 4, 5)

    println(iterable.first())
    println(iterable.first { it % 2 == 0 })
    println(iterable.first { it % 6 == 0 })
}