fun main(args: Array<String>) {
    val iterable: Iterable<*> = listOf(1, "foo", 2.4, false)

    val result = iterable.filterIsInstance(String::class.java)

    println(result)
}