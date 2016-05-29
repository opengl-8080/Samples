fun main(args: Array<String>) {
    val result = mutableListOf(9)
    val iterable: Iterable<Int> = listOf(1, 2, 3, 4,  5)

    println(iterable.filterNotTo(result, { it % 2 == 0 }))
}