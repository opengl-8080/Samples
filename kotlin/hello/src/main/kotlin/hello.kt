fun main(args: Array<String>) {
    val iterable: Iterable<Int> = listOf(1, 2, 3)

    println(iterable.elementAtOrNull(0))
    println(iterable.elementAtOrNull(5))
}