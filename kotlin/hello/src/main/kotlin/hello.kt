fun main(args: Array<String>) {
    val result = mutableListOf(9)
    val iterable: Iterable<Int?> = listOf(1, null, 3, null, 5)

    println(iterable.filterNotNullTo(result))
}