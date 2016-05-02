fun main(args: Array<String>) {
    val integerHoge = Hoge<Int>(10)
    val numberBase: Base<Int> = integerHoge

    println(numberBase.get())
}

interface Base<out T> {

    fun get(): T
}

class Hoge<U>(private val u: U): Base<U> {

    override fun get(): U {
        return this.u
    }
}