fun main(args: Array<String>) {
    val iterable: Iterable<Int> = listOf(1, 2, 3)

    println(iterable.elementAtOrElse(0, { it * 10 }))
    println(iterable.elementAtOrElse(5, { it * 10 }))
}