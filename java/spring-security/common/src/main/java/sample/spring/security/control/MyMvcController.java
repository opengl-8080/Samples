package sample.spring.security.control;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/mvc")
public class MyMvcController {
    
    @GetMapping
    public String hello() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String name = auth.getName();
        Object credentials = auth.getCredentials();
        Object principal = auth.getPrincipal();
        String authorities = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", "));

        System.out.println(
            "name = " + name + "\n" +
            "credentials = " + credentials + "\n" +
            "authorities = " + authorities + "\n" +
            "principal = " + principal
        );
        
        return "test";
    }
}
