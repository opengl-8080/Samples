fun main(args: Array<String>) {
    val mutableList = mutableListOf(3, 1, 2)

    mutableList.sort()
    println(mutableList)

    mutableList.sortDescending()
    println(mutableList)
}