package sample.spring.service;

import org.springframework.security.access.prepost.PreAuthorize;

public class HelloService {

    @PreAuthorize("hasRole('ADMIN')")
    public void admin() {
        System.out.println("Hello admin");
    }
    
    @PreAuthorize("hasRole('USER')")
    public void user() {
        System.out.println("Hello user");
    }
}
