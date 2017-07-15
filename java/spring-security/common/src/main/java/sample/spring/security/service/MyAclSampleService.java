package sample.spring.security.service;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
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
        ObjectIdentityImpl parentId = new ObjectIdentityImpl(Foo.class, 44L);
        MutableAcl parentAcl = this.aclService.createAcl(parentId);
        parentAcl.insertAce(
            parentAcl.getEntries().size(),
            BasePermission.READ,
            new GrantedAuthoritySid(new SimpleGrantedAuthority("READONLY")),
            true
        );
        this.aclService.updateAcl(parentAcl);

        ObjectIdentityImpl childId = new ObjectIdentityImpl(Foo.class, 45L);
        MutableAcl childAcl = this.aclService.createAcl(childId);
        childAcl.setEntriesInheriting(true);
        childAcl.setParent(parentAcl);
        this.aclService.updateAcl(childAcl);
    }
}
