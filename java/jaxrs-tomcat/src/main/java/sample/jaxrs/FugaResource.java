package sample.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("fuga")
public class FugaResource {

    @GET
    public String fuga() {
        return "fuga";
    }
}
