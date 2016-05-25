package sample.web

import sample.ejb.JavaEjb
import sample.ejb.KotlinEjb
import javax.ejb.EJB
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.ws.rs.Path
import javax.ws.rs.GET
import javax.ws.rs.QueryParam


@Path("kotlin")
@RequestScoped
open class HelloResource {

    @Inject
    lateinit private var javaEjbAtInject: JavaEjb
    @EJB
    lateinit private var javaEjbAtEJB: JavaEjb
    @Inject
    lateinit private var kotlinEjbAtInject: KotlinEjb
    @EJB
    lateinit private var kotlinEjbAtEJB: KotlinEjb

    @GET
    open fun hello() : String {
        this.javaEjbAtInject.hello("javaEjbAtInject")
        this.javaEjbAtEJB.hello("javaEjbAtEJB")
        this.kotlinEjbAtInject.hello("kotlinEjbAtInject")
        this.kotlinEjbAtEJB.hello("kotlinEjbAtEJB")

        return "Hello Kotlin Java EE!!"
    }
}