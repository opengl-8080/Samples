package sample.doma2.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {
    
    @Autowired
    private MyService service;
    
    public static void main(String[] args) throws Exception {
        try (ConfigurableApplicationContext ctx = SpringApplication.run(Main.class, args)) {
            Main m = ctx.getBean(Main.class);
            
            try {
                m.service.updateAndThorwException(1, "exception", Exception::new);
            } catch (Exception e) {}

            try {
                m.service.updateAndThorwException(2, "runtime exception", NullPointerException::new);
            } catch (Exception e) {}
            
            m.service.printAll();
        }
    }
}
