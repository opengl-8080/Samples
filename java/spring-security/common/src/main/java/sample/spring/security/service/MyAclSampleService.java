package sample.spring.security.service;

import org.springframework.security.access.prepost.PreAuthorize;
import sample.spring.security.domain.Foo;

public class MyAclSampleService {

    @PreAuthorize("hasPermission(#foo, read)")
    public void read(Foo foo) {
        System.out.println(foo);
    }
    
    @PreAuthorize("hasPermission(#foo, 'hoge')")
    public void hoge(Foo foo) {
        System.out.println(foo);
    }
}
