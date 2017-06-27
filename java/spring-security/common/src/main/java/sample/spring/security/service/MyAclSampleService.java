package sample.spring.security.service;

import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.transaction.annotation.Transactional;
import sample.spring.security.domain.Foo;

@Transactional
public class MyAclSampleService {
    
    private MutableAclService aclService;

    public MyAclSampleService(MutableAclService aclService) {
        this.aclService = aclService;
    }

    public void createObjectIdentity() {
        ObjectIdentity objectIdentity = new ObjectIdentityImpl(Foo.class, 10L);
        MutableAcl acl = this.aclService.createAcl(objectIdentity);
        System.out.println("acl = " + acl);
    }
}
