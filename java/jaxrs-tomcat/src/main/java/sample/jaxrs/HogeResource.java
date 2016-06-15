package sample.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("hoge")
public class HogeResource {

    @GET
    public String hoge() {
        return "hoge";
    }
}
