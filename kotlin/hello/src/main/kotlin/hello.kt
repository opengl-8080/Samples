fun main(args: Array<String>) {
    val mutableList = mutableListOf("aaa", "bb", "c")

    mutableList.sortBy { it.length }

    println(mutableList)
}
