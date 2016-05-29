fun main(args: Array<String>) {
    val iterable: Iterable<String> = listOf("foo", "bar", "fizz", "buzz", "hoge")

    println(iterable.distinctBy { it.length })
}