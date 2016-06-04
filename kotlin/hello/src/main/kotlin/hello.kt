fun main(args: Array<String>) {
    val mutableMap = mutableMapOf("one" to "ONE", "two" to "TWO", "three" to "THREE")

    mutableMap.plusAssign(mapOf("four" to "FOUR"))
    mutableMap.plusAssign("five" to "FIVE")
    mutableMap.plusAssign(listOf("six" to "SIX"))

    mutableMap += "seven" to "SEVEN"

    println(mutableMap)
}