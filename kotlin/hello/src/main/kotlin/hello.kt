fun main(args: Array<String>) {
    val map = mapOf("one" to "ONE", "two" to "TWO", "three" to "THREE")
    val list: List<Pair<String, String>> = map.toList()

    println(list)
}