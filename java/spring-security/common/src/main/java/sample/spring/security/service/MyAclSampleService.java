package sample.spring.security.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
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
        ObjectIdentity objectIdentity = new ObjectIdentityImpl(Foo.class, 10L);
        MutableAcl acl = this.aclService.createAcl(objectIdentity);

        GrantedAuthoritySid permitRead = new GrantedAuthoritySid(new SimpleGrantedAuthority("PERMIT_READ"));
        GrantedAuthoritySid deniedRead = new GrantedAuthoritySid(new SimpleGrantedAuthority("DENIED_READ"));
        acl.insertAce(0, BasePermission.READ, deniedRead, false);
        acl.insertAce(1, BasePermission.READ, permitRead, true);

        this.aclService.updateAcl(acl);
        
        System.out.println("acl = " + acl);
    }
    
    @PreAuthorize("hasPermission(#foo, read)")
    public void read(Foo foo) {
        System.out.println("read(" + foo + ")");
    }
}
