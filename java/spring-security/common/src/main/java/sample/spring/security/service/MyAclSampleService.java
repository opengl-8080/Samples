package sample.spring.security.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.AlreadyExistsException;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import sample.spring.security.domain.Foo;

@Transactional
public class MyAclSampleService {
    
    private MutableAclService aclService;

    public MyAclSampleService(MutableAclService aclService) {
        this.aclService = aclService;
    }
    
    public void init() {
        try {
            ObjectIdentityImpl id44 = new ObjectIdentityImpl(Foo.class, 44L);
            MutableAcl acl44 = this.aclService.createAcl(id44);
            acl44.insertAce(acl44.getEntries().size(), BasePermission.READ, new GrantedAuthoritySid(new SimpleGrantedAuthority("READONLY")), true);
            this.aclService.updateAcl(acl44);

            ObjectIdentityImpl id45 = new ObjectIdentityImpl(Foo.class, 45L);
            MutableAcl acl45 = this.aclService.createAcl(id45);
            acl45.setParent(acl44);
            this.aclService.updateAcl(acl45);

            ObjectIdentityImpl id46 = new ObjectIdentityImpl(Foo.class, 46L);
            this.aclService.createAcl(id46);
        } catch (AlreadyExistsException e) {
            // ignore
        }
    }

    @PreAuthorize("hasPermission(#foo, read)")
    public void read(Foo foo) {
        System.out.println("read(" + foo + ")");
    }
}
