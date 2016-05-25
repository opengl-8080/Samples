package sample.cdi

import javax.enterprise.context.RequestScoped
import javax.inject.Inject

@RequestScoped
open class KotlinCdiBean {

    @Inject
    lateinit private var bean: KotlinCdiBeanBeta

    fun hello() {
        println("Hello Kotlin CDI Bean!!")
        this.bean.hello()
    }
}
