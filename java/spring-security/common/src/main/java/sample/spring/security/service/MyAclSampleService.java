package sample.spring.security.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class MyAclSampleService {
    
    @PreAuthorize("hasPermission(#id, 'sample.spring.security.domain.Foo', read)")
    public void logic(long id) {
        System.out.println("id = " + id);
    }
}
