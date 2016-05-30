fun main(args: Array<String>) {
    val iterable: Iterable<Int> = listOf(1, 2, 3, 4, 5, 6, 7)

    val result1: MutableMap<String, MutableList<Int>> = mutableMapOf("foo" to mutableListOf(9))

    iterable.groupByTo(result1) { if (it % 2 == 0) "偶数" else "奇数" }
    println(result1)

    val result2: MutableMap<String, MutableList<String>> = mutableMapOf("bar" to mutableListOf("BAR"))

    iterable.groupByTo(
        result2,
        { if (it % 2 == 0) "偶数" else "奇数" },
        { "<$it>"}
    )
    println(result2)
}