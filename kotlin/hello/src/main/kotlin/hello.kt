fun main(args: Array<String>) {

    val a = Hoge("a")
    val b = Hoge("a")

    println(a === b)
    println(a == b)
}

class Hoge(val name: String) {

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (!(o is Hoge)) return false

        println("equals()!!")

        return this.name == o.name
    }
}