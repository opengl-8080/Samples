package sample.spring.security.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyMvcController {
    
    @GetMapping("/hello")
    public String hello() {
        System.out.println("Hello");
        return "hello";
    }
}
