package sample.spring.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sample.spring.service.HelloService;

@Controller
public class HelloController {
    
    private HelloService service;

    @Autowired
    public HelloController(HelloService service) {
        this.service = service;
    }

    @GetMapping("/hello")
    public String hello(@RequestParam("p") String p) {
        System.out.println("p=" + p);
        if ("user".equals(p)) {
            this.service.user();
        } else {
            this.service.admin();
        }
        
        return "hello";
    }
}
