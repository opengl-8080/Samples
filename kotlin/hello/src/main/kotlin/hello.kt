fun main(args: Array<String>) {
    val iterable: Iterable<Int> = listOf(1, 2, 3, 4, 5, 6, 7)

    val result1: Map<String, List<Int>> =
        iterable.groupBy { if (it % 2 == 0) "偶数" else "奇数" }

    println(result1)


    val result2: Map<String, List<String>> =
        iterable.groupBy(
            { if (it % 2 == 0) "偶数" else "奇数" },
            { "<$it>"}
        )

    println(result2)
}