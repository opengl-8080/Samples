package sample.spring.security.service;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Component;

@Component
public class MyMethodSecurityService {
    
    @PostAuthorize("returnObject == 'hoge'")
    public String getMessage(String name) {
        System.out.println("name = " + name);
        return name;
    }
}
