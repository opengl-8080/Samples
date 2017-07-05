package sample.spring.security.service;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.AlreadyExistsException;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import sample.spring.security.domain.Foo;

@Transactional
public class MyAclSampleService {
    
    private MutableAclService aclService;

    public MyAclSampleService(MutableAclService aclService) {
        this.aclService = aclService;
    }
    
    public void addPermission() {
        ObjectIdentityImpl objectIdentity = new ObjectIdentityImpl(Foo.class, 10L);
        try {
            MutableAcl acl = this.aclService.createAcl(objectIdentity);
            acl.insertAce(
                acl.getEntries().size(),
                BasePermission.READ,
                new GrantedAuthoritySid(new SimpleGrantedAuthority("aaa")),
                true
            );
            this.aclService.updateAcl(acl);
        } catch (AlreadyExistsException e) {
            System.out.println("skip add permission.");
        }
    }
    
    public void modifyPermission() {
        ObjectIdentity objectIdentity = new ObjectIdentityImpl(Foo.class, 10L);
        MutableAcl acl = (MutableAcl) this.aclService.readAclById(objectIdentity);
        
        acl.insertAce(
            acl.getEntries().size(),
            BasePermission.READ,
            new GrantedAuthoritySid(new SimpleGrantedAuthority("test")),
            true
        );
        
        this.aclService.updateAcl(acl);
    }
}
