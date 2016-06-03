fun main(args: Array<String>) {
    val map = mapOf("one" to "ONE", "two" to "TWO", "three" to "THREE")

    println(map.any { it.key.contains("o") })
    println(map.any { it.key.isEmpty() })
}