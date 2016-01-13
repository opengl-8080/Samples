package sample.doma2.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {
    
    public static void main(String[] args) {
        try (ConfigurableApplicationContext ctx = SpringApplication.run(Main.class, args)) {
            Main m = ctx.getBean(Main.class);
            m.method();
        }
    }
    
    @Autowired
    private TestTableDao dao;

    private void method() {
        this.dao.findAll().forEach(System.out::println);
    }
}
