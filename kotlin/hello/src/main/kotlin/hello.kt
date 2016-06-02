fun main(args: Array<String>) {
    val iterable = listOf(1, 2, 3)

    val result: MutableList<Int> = iterable.toMutableList()

    println(result)
}