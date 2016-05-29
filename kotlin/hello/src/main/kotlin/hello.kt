fun main(args: Array<String>) {
    val resultMap = mutableMapOf(9 to 99)
    val iterable: Iterable<Int> = listOf(1, 2, 3)

    iterable.associateTo(resultMap) { it to it*100 }

    println(resultMap)
}