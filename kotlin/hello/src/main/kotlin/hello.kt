fun main(args: Array<String>) {
    val iterable: Iterable<Int> = listOf(1, 2, 3)

    val average = iterable.average()

    println(average)
}