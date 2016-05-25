package sample.cdi

import javax.enterprise.context.RequestScoped

@RequestScoped
open class KotlinCdiBeanBeta {

    open fun hello() {
        println("KotlinCdiBeanBeta")
    }
}
