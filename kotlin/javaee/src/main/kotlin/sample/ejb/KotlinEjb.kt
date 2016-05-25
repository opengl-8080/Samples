package sample.ejb

import javax.ejb.Stateless

@Stateless
open class KotlinEjb {

    fun hello(name: String) {
        println("[$name] Hello Kotlin EJB!!")
    }
}
