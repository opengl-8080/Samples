fun main(args: Array<String>) {
    val list = listOf("0:one", "1:two", "2:three", "3:four", "4:five")
    println(list.slice(2..4))

    val indices = listOf(1, 3, 4)
    println(list.slice(indices))
}