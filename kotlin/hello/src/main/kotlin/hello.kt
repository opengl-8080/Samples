fun main(args: Array<String>) {
    val iterable: Iterable<Int> = listOf(1, 2, 3, 4, 5)
    val other: Iterable<Int> = listOf(2, 3, 5, 2)

    val result: Set<Int> = iterable.intersect(other)

    println(result)
}