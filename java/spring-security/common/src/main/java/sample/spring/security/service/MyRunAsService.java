package sample.spring.security.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class MyRunAsService {
    
    private MyRunAsService2 service2;

    public MyRunAsService(MyRunAsService2 service2) {
        this.service2 = service2;
    }

    @Secured({"ROLE_USER", "RUN_AS_HOGE", "RUN_AS_FUGA"})
    public void secured() {
        this.printAuthorities("secured");
        this.service2.printAuthorities("service2");
    }
    
    public void nonSecured() {
        this.printAuthorities("nonSecured");
    }
    
    public void printAuthorities(String tag) {
        System.out.println("[" + tag + "]");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .forEach(System.out::println);
    }
}
