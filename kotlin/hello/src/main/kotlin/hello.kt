fun main(args: Array<String>) {
    val list = mutableListOf(1, 2, 3)

    println("[before] list = $list")

    val asReversed = list.asReversed()

    println("[after asReversed] list = $list")
    println("[after asReversed] asReversed = $asReversed")

    list += 9
    asReversed += 99

    println("[after modify] list = $list")
    println("[after modify] asReversed = $asReversed")
}