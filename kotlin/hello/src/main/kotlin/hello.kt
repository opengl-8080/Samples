fun main(args: Array<String>) {
    val map = mapOf("one" to "ONE", "two" to "TWO", "three" to "THREE")

    println(map.getOrElse("one", {"DEFAULT"}))
    println(map.getOrElse("nine", {"DEFAULT"}))
}