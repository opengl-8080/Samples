package sample.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("hello")
public class SampleResource {

    @GET
    public String hello() {
        return "Hello JAX-RS on Tomcat";
    }
}
