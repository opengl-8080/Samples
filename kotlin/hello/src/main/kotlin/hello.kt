fun main(args: Array<String>) {
    val iterable: Iterable<Int> = listOf(1, 2, 3)

    println(iterable.all { it < 4 })
    println(iterable.all { it < 3 })
}