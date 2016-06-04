fun main(args: Array<String>) {
    val mutableMap = mutableMapOf("one" to "ONE", "two" to "TWO", "three" to "THREE")

    mutableMap.putAll(mapOf("four" to "FOUR", "five" to "FIVE"))
    println(mutableMap)

    mutableMap.putAll(listOf("six" to "SIX"))
    println(mutableMap)
}