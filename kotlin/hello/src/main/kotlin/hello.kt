fun main(args: Array<String>) {
    val iterable = listOf(1, 2, 3, 4, 5)

    println(iterable.takeWhile { it < 5 })
}