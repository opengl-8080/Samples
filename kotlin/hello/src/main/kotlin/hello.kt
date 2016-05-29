fun main(args: Array<String>) {
    val result = mutableListOf<Number>(9)
    val iterable: Iterable<*> = listOf(1, "foo", 2.4, false)

    iterable.filterIsInstanceTo(result, Integer::class.java)

    println(result)
}