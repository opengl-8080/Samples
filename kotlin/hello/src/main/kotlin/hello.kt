fun main(args: Array<String>) {
    val iterable1 = listOf(1, 2, 3, 4, 5)
    val iterable2 = listOf(2, 4, 5);

    println(iterable1.minus(1))
    println(iterable1.minus(iterable2))
    println(iterable1 - iterable2)
}