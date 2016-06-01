fun main(args: Array<String>) {
    val iterable = listOf("a", "b", null, "c")

    println(iterable.requireNoNulls())
}