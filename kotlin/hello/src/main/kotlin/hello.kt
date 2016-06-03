fun main(args: Array<String>) {
    val map = mapOf("one" to "ONE", "two" to "TWO", "three" to "THREE")

    println(map.containsValue("one"))
    println(map.containsValue("ONE"))
}