package sample.jaxrs;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class MyApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        System.out.println("getClasses");
        Set<Class<?>> classes = new HashSet<>();

        classes.add(FugaResource.class);

        return classes;
    }
}
