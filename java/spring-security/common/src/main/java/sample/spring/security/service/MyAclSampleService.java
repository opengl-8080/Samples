package sample.spring.security.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import sample.spring.security.domain.Foo;

@Transactional
public class MyAclSampleService {

    @PreAuthorize("hasPermission(#foo, read)")
    public void read(Foo foo) {
        System.out.println("read(" + foo + ")");
    }
    
    @PreAuthorize("hasPermission(#foo, write)")
    public void write(Foo foo) {
        System.out.println("write(" + foo + ")");
    }
}
