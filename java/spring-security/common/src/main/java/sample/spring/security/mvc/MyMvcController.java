package sample.spring.security.mvc;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyMvcController {
    
    @GetMapping("/user")
    public String foo(@AuthenticationPrincipal User user) {
        System.out.println("username=" + user.getUsername() + ", authorities=" + user.getAuthorities());
        return "User!!";
    }
}
