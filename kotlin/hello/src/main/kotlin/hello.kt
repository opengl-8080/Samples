fun main(args: Array<String>) {
    val map = mapOf("one" to "ONE", "two" to "TWO", "three" to "THREE")

    println(map.all { it.key is String })
    println(map.all { it.key.contains("o") })
}