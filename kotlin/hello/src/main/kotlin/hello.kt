fun main(args: Array<String>) {
    val iterable: Iterable<String> = listOf("foo", "bar", "fizz")

    println(iterable.maxBy { it.length })
}