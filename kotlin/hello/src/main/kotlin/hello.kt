fun main(args: Array<String>) {
    val iterable1 = listOf(1, 2, 3)

    val iterable2 = listOf("one", "two", "three", "four")
    val result1: List<String> = iterable1.zip(iterable2, {a, b -> "$b($a)"})
    println(result1)

    val array = arrayOf(1.1, 2.2)
    val result2: List<Int> = iterable1.zip(array, {a, b -> (a + b*10).toInt()})
    println(result2)
}