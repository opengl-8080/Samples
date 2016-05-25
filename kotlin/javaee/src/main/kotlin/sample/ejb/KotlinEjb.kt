package sample.ejb

import sample.cdi.KotlinCdiBean
import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
open class KotlinEjb {

    @Inject
    lateinit private var bean: KotlinCdiBean

    fun hello() {
        this.bean.hello()
    }
}
