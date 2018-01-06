package sample.kdoc

/**
 * クラスの説明.
 */
class TopLevelClass {
    /**
     * コンパニオンオブジェクトの説明.
     */
    companion object {
        /**
         * コンパニオンオブジェクトのプロパティの説明.
         */
        val value = "test"
    }

    /**
     * プロパティの説明.
     */
    var value: String? = null

    /**
     * メソッドの説明.
     * 
     * @args arg 引数の説明
     * @return return 戻り値の説明
     */
    fun method(arg: String = "default") = "method"
}

/**
 * トップレベルの関数の説明.
 */
fun topLevelFunction() {
}

/**
 * トップレベルのプロパティの説明.
 */
val topLevelProperty = "test"
