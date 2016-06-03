fun main(args: Array<String>) {
    val map = mapOf("one" to "ONE", "two" to "TWO", "three" to "THREE")

    map.forEach { println("key=${it.key}, value=${it.value}") }
}