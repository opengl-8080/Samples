fun main(args: Array<String>) {
    val iterable: Iterable<Int> = listOf(1, 2, 3, 4, 5)

    println(iterable.last())
    println(iterable.last { it % 2 == 0 })
    println(iterable.last { it % 6 == 0 })
}