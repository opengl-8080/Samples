package sample.spring.security.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class HelloService {
    
    @PreAuthorize("hasRole('USER')")
    public void hello() {
        System.out.println("Hello Service");
    }
}
