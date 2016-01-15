package sample.doma2.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Main {
    
    @Autowired
    private MyService service;
    
    public static void main(String[] args) throws Exception {
        try (ConfigurableApplicationContext ctx = SpringApplication.run(Main.class, args)) {
            Main m = ctx.getBean(Main.class);
            m.service.method();
        }
    }
}
