package sample.spring.security.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.model.MutableAclService;
import sample.spring.security.domain.Foo;

public class MyAclSampleService {
    
    @PreAuthorize("hasPermission(#foo, admin)")
    public void logic(Foo foo) {
        System.out.println("foo=" + foo);
    }
    
    private MutableAclService aclService;
//    private JdbcTemplate jdbcTemplate;

//    @Transactional
    public void save() {
//        ObjectIdentityImpl oi = new ObjectIdentityImpl(Foo.class, 44L);
//        MutableAcl acl = this.aclService.createAcl(oi);
//        System.out.println(acl.getClass());
//        acl.insertAce(acl.getEntries().size(), BasePermission.READ, new GrantedAuthoritySid("role_hoge"), true);
//        this.aclService.updateAcl(acl);

//        Sid sid = new PrincipalSid("Samantha");
//        Permission permission = BasePermission.ADMINISTRATION;
//
//        MutableAcl acl = null;
//        try {
//            acl = (MutableAcl) this.aclService.readAclById(oi);
//        } catch (NotFoundException e) {
//            acl = this.aclService.createAcl(oi);
//        }
//
//        acl.insertAce(acl.getEntries().size(), permission, sid, true);
//        this.aclService.updateAcl(acl);
    }
    
//    @Transactional
    public void list() {
//        this.printTable("ACL_SID");
//        this.printTable("ACL_CLASS");
//        this.printTable("ACL_OBJECT_IDENTITY");
//        this.printTable("ACL_ENTRY");
    }
//    
//    private void printTable(String tableName) {
//        System.out.println("[" + tableName + "]");
//        List<Map<String, Object>> records = this.jdbcTemplate.queryForList("select * from " + tableName);
//        records.forEach(System.out::println);
//
//        System.out.println();
//    }
//    
//    public void setAclService(MutableAclService aclService) {
//        this.aclService = aclService;
//    }
//
//    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
}
