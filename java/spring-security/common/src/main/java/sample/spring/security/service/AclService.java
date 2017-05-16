package sample.spring.security.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.transaction.annotation.Transactional;
import sample.spring.security.domain.Foo;

import java.util.List;
import java.util.Map;

public class AclService {
    
    private MutableAclService aclService;
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void save() {
        ObjectIdentityImpl oi = new ObjectIdentityImpl(Foo.class, 44L);
        Sid sid = new PrincipalSid("Samantha");
        Permission permission = BasePermission.ADMINISTRATION;

        MutableAcl acl = null;
        try {
            acl = (MutableAcl) this.aclService.readAclById(oi);
        } catch (NotFoundException e) {
            acl = this.aclService.createAcl(oi);
        }

        acl.insertAce(acl.getEntries().size(), permission, sid, true);
        this.aclService.updateAcl(acl);
    }
    
    @Transactional
    public void list() {
        this.printTable("ACL_SID");
        this.printTable("ACL_CLASS");
        this.printTable("ACL_OBJECT_IDENTITY");
        this.printTable("ACL_ENTRY");
    }
    
    private void printTable(String tableName) {
        System.out.println("[" + tableName + "]");
        List<Map<String, Object>> records = this.jdbcTemplate.queryForList("select * from " + tableName);
        records.forEach(System.out::println);

        System.out.println();
    }
    
    public void setAclService(MutableAclService aclService) {
        this.aclService = aclService;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
