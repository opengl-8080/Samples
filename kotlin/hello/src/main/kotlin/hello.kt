fun main(args: Array<String>) {
    val map = mapOf("one" to "ONE", "two" to "TWO", "three" to "THREE")

    println(map.map { if (it.key == "two") null else "${it.key}=${it.value}" })
    println(map.mapNotNull { if (it.key == "two") null else "${it.key}=${it.value}" })
}