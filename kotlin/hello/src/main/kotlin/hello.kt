fun main(args: Array<String>) {
    val iterable = listOf(1, 2, 3, 4, 5)

    val result: Iterable<IndexedValue<Int>> = iterable.withIndex()

    result.forEach { println("index=${it.index}, value=${it.value}") }
}