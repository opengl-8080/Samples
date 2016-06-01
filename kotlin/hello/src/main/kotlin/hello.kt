fun main(args: Array<String>) {
    val iterable1 = listOf(9)
    val iterable2 = listOf(1, 2, 3, 4)


    println(iterable1.singleOrNull())

    println(iterable2.singleOrNull {it == 4})
    println(iterable2.singleOrNull())
}
