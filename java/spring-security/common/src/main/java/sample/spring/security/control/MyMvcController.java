package sample.spring.security.control;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mvc")
public class MyMvcController {
    
    @PostMapping
    public String hello() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("auth.name = " + auth.getName());
        
        return "test";
    }
}
