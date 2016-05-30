fun main(args: Array<String>) {
    val iterable: Iterable<Int> = listOf(1, 2, 3, 4, 5)
    val buffer: Appendable = StringBuilder()

    iterable.joinTo(buffer)

    println(buffer)
}