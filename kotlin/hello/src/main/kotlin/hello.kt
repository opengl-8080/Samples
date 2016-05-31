fun main(args: Array<String>) {
    val iterable: Iterable<*> = listOf(1, "foo", 2.4, false)

    println(iterable.filterIsInstance<Number>())
    println(iterable.filterIsInstance(String::class.java))
}