fun main(args: Array<String>) {
    val iterable = listOf(1, 1, 2, 3, 3)

    val result: Set<Int> = iterable.toSet()

    println(result)
}