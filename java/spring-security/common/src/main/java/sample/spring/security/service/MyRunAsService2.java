package sample.spring.security.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class MyRunAsService2 {
    
    @Secured("ROLE_RUN_AS_HOGE")
    public void printAuthorities(String tag) {
        System.out.println("[" + tag + "]");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .forEach(System.out::println);
    }
}
