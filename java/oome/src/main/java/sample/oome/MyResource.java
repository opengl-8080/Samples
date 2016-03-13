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
    
//    private static List<BigObject> list = new ArrayList<>();
    private BigObject bigObject;
    
    @GET
    public void execute(@QueryParam("size") int size) {
        this.bigObject = new BigObject(size);
//        list.add(new BigObject(size));
        System.out.println("done (" + size + ")");
    }
    
    public static class BigObject {
        public byte[][] data;
        
        public BigObject(int size) {
            this.data = new byte[1024][size];
        }
    }
}
