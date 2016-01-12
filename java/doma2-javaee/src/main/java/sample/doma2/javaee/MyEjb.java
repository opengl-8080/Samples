package sample.doma2.javaee;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class MyEjb {
    
    @Inject
    private TestTableDao dao;
    
    public void execute() {
        this.dao.findAll().forEach(System.out::println);
    }
}
