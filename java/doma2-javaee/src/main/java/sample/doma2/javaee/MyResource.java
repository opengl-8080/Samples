package sample.doma2.javaee;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("hello")
@RequestScoped
public class MyResource {
    @Inject
    private MyEjb ejb;
    
    @GET
    public void hello() {
        this.ejb.execute();
    }
}
