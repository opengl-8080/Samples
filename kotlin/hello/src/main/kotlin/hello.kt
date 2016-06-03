fun main(args: Array<String>) {
    val list = listOf(1, 2, 3, 4, 5)

    println(list.dropLastWhile { it > 2 })
}