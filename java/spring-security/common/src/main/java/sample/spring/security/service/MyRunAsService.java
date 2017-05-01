package sample.spring.security.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import sample.spring.security.runas.RunAs;

public class MyRunAsService {

    @PreAuthorize("hasRole('ROLE_USER')")
    @RunAs({"HOGE", "FUGA"})
    public void secured() {
        this.printAuthorities("secured");
    }
    
    public void nonSecured() {
        this.printAuthorities("nonSecured");
    }
    
    public void printAuthorities(String tag) {
        System.out.println("[" + tag + "]");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication.class = " + auth.getClass());
        auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .forEach(System.out::println);
    }
}
