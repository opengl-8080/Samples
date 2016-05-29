fun main(args: Array<String>) {
    val iterable: Iterable<String> = listOf("a", "b", "c")

    println(iterable.foldIndexed("Z", { index, buf, value -> "$buf $index=$value" }))
}