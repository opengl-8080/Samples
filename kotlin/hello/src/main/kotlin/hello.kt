fun main(args: Array<String>) {
    val map1 = mapOf("one" to "ONE", "two" to "TWO", "three" to "THREE")
    val map2: Map<String, String>? = null

    println(map1.orEmpty())
    println(map2.orEmpty())
}