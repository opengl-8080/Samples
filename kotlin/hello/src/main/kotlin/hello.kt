fun main(args: Array<String>) {
    val iterable1 = listOf(1, 2, 3, 4)
    val iterable2 = listOf(2, 4, 5, 6)

    val result: Set<Int> = iterable1.union(iterable2)

    println(result)
}