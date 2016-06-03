fun main(args: Array<String>) {
    val iterable1 = listOf(1, 2, 3, 4)
    val iterable2 = listOf(2, 3)
    val iterable3 = listOf(4, 5)

    println(iterable1.containsAll(iterable2))
    println(iterable1.containsAll(iterable3))
}