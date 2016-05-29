fun main(args: Array<String>) {
    val result = mutableListOf(999)
    val iterable: Iterable<Iterable<Int>> = listOf(
        listOf(1, 2, 3),
        listOf(4, 5, 6),
        listOf(7, 8, 9)
    )

    println(iterable.flatMapTo(result) { it })
}