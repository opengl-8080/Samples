fun main(args: Array<String>) {
    val iterable = listOf(1, 2, 3)

    val result: MutableCollection<Int> = mutableListOf(9)

    iterable.toCollection(result)

    println(result)
}