fun main(args: Array<String>) {
    val resultMap = mutableMapOf(9 to 99)
    val iterable: Iterable<Int> = listOf(1, 2, 3)

    iterable.associateByTo(resultMap, { it*10 }, { it*100 })

    println(resultMap)
}