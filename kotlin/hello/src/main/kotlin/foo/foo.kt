package foo

object Foo {
    fun String.hoge() {
        println("Foo.hoge")
    }
}

fun String.hoge() {
    println("foo.hoge")
}
