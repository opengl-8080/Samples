fun main(args: Array<String>) {
    val map = mapOf("one" to "ONE", "two" to "TWO", "three" to "THREE")

    println(map.maxBy { it.key })
    println(map.minBy { it.key })
}