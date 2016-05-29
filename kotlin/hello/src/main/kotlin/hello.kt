fun main(args: Array<String>) {
    val result = mutableListOf(9);
    val iterable: Iterable<Int> = listOf(1, 2, 3)

    iterable.filterIndexedTo(result) { index, value ->
        index == 0 || value == 3
    }

    println(result)
}