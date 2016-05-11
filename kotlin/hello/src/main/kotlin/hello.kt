fun main(args: Array<String>) {
}

open class Parent {
    open fun getValue() = "parent"
}

class Child: Parent() {
    private val value = "child"

    override fun getValue() = this.value
}