fun main(args: Array<String>) {
    val map = mapOf("one" to "ONE", "two" to "TWO", "three" to "THREE")

    println(map.plus(mapOf("four" to "FOUR")))
    println(map.plus("five" to "FIVE"))
    println(map.plus(listOf("six" to "SIX")))

    println(map + ("seven" to "SEVEN"))
}