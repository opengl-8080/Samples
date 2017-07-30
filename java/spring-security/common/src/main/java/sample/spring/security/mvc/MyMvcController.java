package sample.spring.security.mvc;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyMvcController {
    
    @GetMapping("/foo")
    public String foo() {
        return "FOO!!";
    }
}
