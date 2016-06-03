fun main(args: Array<String>) {
    val iterable = listOf("a", "b", "c")

    println(iterable.foldRight("Z", { value, buf -> buf + value }))
}