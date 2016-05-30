fun main(args: Array<String>) {
    val iterable: Iterable<Int> = listOf(1, 2, 3, 4, 5)
    val result = mutableListOf("Z")

    iterable.mapIndexedNotNullTo(result) { index, value ->
        if (value % 2 == 0) "$index($value)" else null
    }

    println(result)
}