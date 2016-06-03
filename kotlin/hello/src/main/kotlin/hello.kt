fun main(args: Array<String>) {
    val map = mapOf("one" to "ONE", "two" to "TWO", "three" to "THREE")

    println(map.count())
    println(map.count { it.key.contains("t") })
}