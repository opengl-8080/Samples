package sample.spring.security.mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyMvcController {
    
    @GetMapping("/hello")
    public String hello() {
        System.out.println("Hello");
        return "hello";
    }
}
