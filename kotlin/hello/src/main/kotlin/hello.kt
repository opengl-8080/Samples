fun main(args: Array<String>) {
    val iterable: Iterable<Int> = listOf(1, 2, 3)

    println(iterable.filterIndexed { index, value ->
        index == 0 || value == 3
    })
}