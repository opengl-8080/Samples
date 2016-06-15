package sample.jaxrs;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

@ApplicationPath("/api")
public class MyApplication extends Application {

    public MyApplication() {
        System.out.println("default");
    }

    public MyApplication(@Context ServletConfig sc) {
        System.out.println("sc = " + sc);
    }

    public MyApplication(@Context ServletConfig sc1, @Context ServletContext sc2) {
        System.out.println("sc1 = " + sc1 + ", sc2 = " + sc2);
    }
}
