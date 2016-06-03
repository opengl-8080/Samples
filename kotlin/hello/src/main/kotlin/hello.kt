fun main(args: Array<String>) {
    val intCollection = listOf(1, 2, 3, 4)

    val intArray: IntArray = intCollection.toIntArray()

    intArray.forEach { println(it) }
}