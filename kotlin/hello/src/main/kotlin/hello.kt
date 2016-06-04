fun main(args: Array<String>) {
    val map = mapOf("one" to "ONE", "two" to "TWO", "three" to "THREE")

    println(map.mapKeys { "<${it.key}>" })
    println(map.mapValues { "<${it.value}>" })
}