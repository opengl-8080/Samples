fun main(args: Array<String>) {
    val mutableIterable = mutableListOf(1, 2, 3, 1, 2, 3)

    mutableIterable.retainAll { it == 3 }

    println(mutableIterable)
}