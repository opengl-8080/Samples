fun main(args: Array<String>) {
    val mutableCollection = mutableListOf(1, 2, 3)
    val iterable = listOf(7, 8)

    mutableCollection.plusAssign(iterable)
    println(mutableCollection)

    mutableCollection += 9
    println(mutableCollection)
}
