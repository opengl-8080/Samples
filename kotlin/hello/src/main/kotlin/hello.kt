fun main(args: Array<String>) {
    val mutableIterable = mutableListOf(1, 2, 3, 1, 2, 3)

    mutableIterable.removeAll { it == 3 }

    println(mutableIterable)
}