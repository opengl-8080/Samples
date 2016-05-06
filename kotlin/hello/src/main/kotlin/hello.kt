fun main(args: Array<String>) {
    val map = mapOf(Pair("one", "ONE"), Pair("two", "TWO"), Pair("three", "THREE"))
    val keys = listOf("one", "three")

    val list: List<String> =
                            map
                                .filterKeys { keys.contains(it) }
                                .values
                                .toList()

    println(list)
}

