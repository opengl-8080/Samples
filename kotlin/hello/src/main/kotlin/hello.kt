fun main(args: Array<String>) {
    val iterable = listOf("one" to 1, "two" to 2, "three" to 3)

    val result: Pair<List<String>, List<Int>> = iterable.unzip()

    println(result)
}