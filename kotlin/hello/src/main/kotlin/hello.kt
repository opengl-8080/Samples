fun main(args: Array<String>) {
    val list = listOf("a", "b", "c")

    println(list.reduceRight { value, tmp ->
        println("value=$value, tmp=$tmp")
        tmp + ":" + value
    })
}