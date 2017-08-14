package sample.thymeleaf;

import org.springframework.stereotype.Component;

@Component
public class MySpringBean {
    
    public String hello() {
        return "Hello MySpringBean!!";
    }
}
