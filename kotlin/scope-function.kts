test("let") {
    "hoge".let {
        println("this=$this, it=$it")
        "return value"
    }
}

test("with") {
    with("hoge") {
        println("this=$this")
        "return value"
    }
}

test("run") {
    "hoge".run {
        println("this=$this")
        "return value"
    }
}

test("apply") {
    "hoge".apply {
        println("this=$this")
    }
}

test("also") {
    "hoge".also {
        println("this=$this, it=$it")
    }
}

fun test(name: String, testCase: () -> Any) {
    println("\n=====$name=====")
    val returnValue = testCase()
    println("returnValue=$returnValue")
}