fun main(args: Array<String>) {
    val intCollection = listOf(1, 2, 3, 4)

    val intArray: Array<Int> = intCollection.toTypedArray()

    intArray.forEach { println(it) }
}