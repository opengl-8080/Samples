fun main(args: Array<String>) {
    val iterable: Iterable<Int> = listOf(1, 2, 3)

    iterable.forEachIndexed { index, value -> println("$index = $value") }
}