fun main(args: Array<String>) {
    val mutableMap = mutableMapOf("one" to "ONE", "two" to "TWO", "three" to "THREE")

    println(mutableMap.getOrPut("one", {"DEFAULT"}))
    println(mutableMap)
    println(mutableMap.getOrPut("four", {"DEFAULT"}))
    println(mutableMap)
}