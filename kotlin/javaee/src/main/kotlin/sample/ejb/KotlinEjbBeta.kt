package sample.ejb

import javax.ejb.Stateless

@Stateless
open class KotlinEjbBeta {

    fun hello() {
        println("KotlinEjbBeta");
    }
}
