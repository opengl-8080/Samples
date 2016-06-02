fun main(args: Array<String>) {
    val iterable = listOf("foo" to "FOO", "bar" to "BAR")

    val map = iterable.toMap()

    println(map)

    val result = mutableMapOf("fizz" to "FIZZ")

    iterable.toMap(result)

    println(result)
}