fun main(args: Array<String>) {
    val iterable = listOf("a", "b", "c")

    println(iterable.reduce {tmp, value ->
        println("tmp=$tmp, value=$value")
        tmp + ":" + value
    })
}