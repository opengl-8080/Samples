fun main(args: Array<String>) {
    val iterable: Iterable<Int?> = listOf(1, null, 3, null, 5)

    println(iterable.filterNotNull())
}