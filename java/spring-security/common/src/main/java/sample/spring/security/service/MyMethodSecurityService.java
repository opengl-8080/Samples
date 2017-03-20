package sample.spring.security.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class MyMethodSecurityService {
    
    @PreAuthorize("hasAuthority('ADMIN')")
    public String getMessage() {
        return "Hello Method Security!!";
    }
}
