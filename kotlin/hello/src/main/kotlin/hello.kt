fun main(args: Array<String>) {
    val iterable1 = listOf(1, 2, 3)
    val iterable2 = listOf(3, 5)

    println(iterable1.plus(6))
    println(iterable1.plus(iterable2))
    println(iterable1 + iterable2)
}