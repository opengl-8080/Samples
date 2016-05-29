fun main(args: Array<String>) {
    val iterable: Iterable<Int> = listOf(1, 2, 3)

    val map = iterable.associate { it to it*10 }

    println(map)
}