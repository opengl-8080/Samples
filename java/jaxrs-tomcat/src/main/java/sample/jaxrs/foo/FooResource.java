package sample.jaxrs.foo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("foo")
public class FooResource {

    @GET
    public String foo() {
        return "foo";
    }
}
