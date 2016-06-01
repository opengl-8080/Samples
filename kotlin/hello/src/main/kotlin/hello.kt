fun main(args: Array<String>) {
    val iterable1 = listOf(1, 2, 3)
    val iterable2 = listOf<Int>();

    println(iterable1.none())
    println(iterable2.none())

    println(iterable1.none {it < 2})
    println(iterable1.none {it < 1})
}