fun main(args: Array<String>) {
    val iterable: Iterable<Int> = listOf(1, 2, 3, 4,  5)

    println(iterable.find { it % 2 == 0 })
}