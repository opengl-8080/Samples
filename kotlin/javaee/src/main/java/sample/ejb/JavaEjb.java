package sample.ejb;

import javax.ejb.Stateless;

@Stateless
public class JavaEjb {

    public void hello(String name) {
        System.out.println("[" + name + "] Hello Java EJB!!");
    }
}
