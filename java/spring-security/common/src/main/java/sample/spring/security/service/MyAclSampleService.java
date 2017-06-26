package sample.spring.security.service;

import org.springframework.security.access.prepost.PreAuthorize;
import sample.spring.security.domain.Foo;

public class MyAclSampleService {
    
    @PreAuthorize("hasPermission(#foo, read)")
    public void read(Foo foo) {
        System.out.println("[read] foo=" + foo);
    }

    @PreAuthorize("hasPermission(#foo, write)")
    public void write(Foo foo) {
        System.out.println("[write] foo=" + foo);
    }

    @PreAuthorize("hasPermission(#foo, create)")
    public void create(Foo foo) {
        System.out.println("[create] foo=" + foo);
    }

    @PreAuthorize("hasPermission(#foo, delete)")
    public void delete(Foo foo) {
        System.out.println("[delete] foo=" + foo);
    }

    @PreAuthorize("hasPermission(#foo, admin)")
    public void admin(Foo foo) {
        System.out.println("[admin] foo=" + foo);
    }
}
