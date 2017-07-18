package sample.spring.security.test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;

public class MyTestService {
    
    @PreAuthorize("authenticated")
    public String getMessage() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return "Hello " + name;
    }
}
