fun main(args: Array<String>) {
    val list = listOf(1, 2, 3)

    println(list.getOrElse(0, { 9 }))
    println(list.getOrElse(4, { 9 }))
}