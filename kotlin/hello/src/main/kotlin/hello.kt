import foo.*
import foo.Foo.hoge

fun main(args : Array<String>) {
    Hoge().method()
}

class Hoge {
    companion object Hoge {
        fun String.hoge() {
            println("Hoge.Hoge.hoge")
        }
    }
    
    fun method() {
        fun String.hoge() {
            println("Hoge.method.hoge")
        }
        
        "extensions".hoge()
    }
    
    fun String.hoge() {
        println("Hoge.hoge")
    }
}

fun String.hoge() {
    println("hoge")
}
