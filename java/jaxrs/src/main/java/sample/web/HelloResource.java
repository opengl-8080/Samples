package sample.web;

import javax.enterprise.context.SessionScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.io.Serializable;


@Path("hello")
@SessionScoped
public class HelloResource implements Serializable {

    @PathParam("hoge")
    private String pathParam;

    @QueryParam("fuga")
    private String queryParam;

    @GET
    @Path("{hoge}")
    public String hello() {
        System.out.println(this.hashCode() + ", " + this.getClass());
        return "Hello JAX-RS (pathParam=" + this.pathParam + ", queryParam=" + queryParam + ")";
    }
}
