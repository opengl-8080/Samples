fun main(args: Array<String>) {
    val iterable = listOf(1, 2, 3)

    println(iterable.sumByDouble { it * 1.5 })
}