package sample.ejb;

import sample.cdi.KotlinCdiBean;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class JavaEjb {

    @Inject
    private KotlinCdiBean kotlinCdiBean;

    public void hello(String name) {
        System.out.println("[" + name + "] Hello Java EJB!!");
        this.kotlinCdiBean.hello();
    }
}
