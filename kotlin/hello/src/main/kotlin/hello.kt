fun main(args: Array<String>) {
    val iterable: Iterable<Int> = listOf(1, 2, 3, 4, 5)

    val result = mutableListOf("<9>")
    iterable.mapTo(result) { "<$it>" }

    println(result)
}