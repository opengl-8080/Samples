package sample.spring.security.service;

import org.springframework.security.access.prepost.PreAuthorize;
import sample.spring.security.domain.Foo;

public class MyAclSampleService {
    
    @PreAuthorize("hasPermission(#foo, read)")
    public void logic(Foo foo) {
        System.out.println("foo=" + foo);
    }
}
