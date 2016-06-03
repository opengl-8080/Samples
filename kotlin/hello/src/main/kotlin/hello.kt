fun main(args: Array<String>) {
    val iterable1 = listOf(1, 2, 3, 4)
    val iterable2 = listOf<Int>()

    println(iterable1.orEmpty())
    println(iterable2.orEmpty())
}