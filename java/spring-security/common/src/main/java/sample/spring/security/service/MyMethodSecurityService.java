package sample.spring.security.service;

import org.springframework.security.access.prepost.PreAuthorize;

public class MyMethodSecurityService {

    @PreAuthorize("hasAuthority('ADMIN')")
    public String getMessage() {
        return "Hello Method Security!!";
    }
}
