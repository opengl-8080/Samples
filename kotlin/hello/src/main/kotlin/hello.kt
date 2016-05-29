fun main(args: Array<String>) {
    val iterable: Iterable<String> = listOf("a", "b", "c")

    println(iterable.fold("Z", { buf, value -> buf + value }))
}