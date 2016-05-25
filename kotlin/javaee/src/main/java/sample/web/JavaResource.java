package sample.web;

import sample.ejb.JavaEjb;
import sample.ejb.KotlinEjb;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("java")
@RequestScoped
public class JavaResource {

    @Inject
    private JavaEjb javaEjbAtInject;
    @EJB
    private JavaEjb javaEjbAtEJB;
    @Inject
    private KotlinEjb kotlinEjbAtInject;
    @EJB
    private KotlinEjb kotlinEjbAtEJB;

    @GET
    public String hello() {
        this.javaEjbAtInject.hello("javaEjbAtInject");
        this.javaEjbAtEJB.hello("javaEjbAtEJB");
        this.kotlinEjbAtInject.hello("kotlinEjbAtInject");
        this.kotlinEjbAtEJB.hello("kotlinEjbAtEJB");
        return "Java Resource";
    }
}
