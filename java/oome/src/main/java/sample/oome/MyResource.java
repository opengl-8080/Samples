package sample.oome;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("")
@RequestScoped
public class MyResource {

    private List<BigObject> list = new ArrayList<>();
    
    @GET
    public void execute(@QueryParam("size") int size) {
        this.list.add(new BigObject(size));
        System.out.println("done (" + size + "MB)");
    }
    
    public static class BigObject {
        public byte[][][] data;
        
        public BigObject(int size) {
            this.data = new byte[1024][1024][size];
        }
    }
}
