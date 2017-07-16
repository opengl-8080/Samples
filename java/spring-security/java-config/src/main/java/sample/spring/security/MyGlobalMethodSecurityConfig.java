package sample.spring.security;

import org.springframework.cache.support.NoOpCache;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionFactory;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.PermissionFactory;
import org.springframework.security.acls.domain.SpringCacheBasedAclCache;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import sample.spring.security.acl.MyPermission;

import javax.sql.DataSource;

@EnableGlobalMethodSecurity(prePostEnabled=true)
public class MyGlobalMethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScripts("/sql/create_acl_tables.sql")
                .addScripts("/sql/insert_acl_tables.sql")
                .build();
    }

    @Bean
    public PermissionEvaluator permissionEvaluator(AclService aclService, PermissionFactory permissionFactory) {
        AclPermissionEvaluator aclPermissionEvaluator = new AclPermissionEvaluator(aclService);
        aclPermissionEvaluator.setPermissionFactory(permissionFactory);
        return aclPermissionEvaluator;
    }

    @Bean
    public AclService aclService(DataSource dataSource, LookupStrategy lookupStrategy, AclCache aclCache) {
        return new JdbcMutableAclService(dataSource, lookupStrategy, aclCache);
    }

    @Bean
    public PermissionFactory permissionFactory() {
        return new DefaultPermissionFactory(MyPermission.class);
    }

    @Bean
    public LookupStrategy lookupStrategy(
        DataSource dataSource,
        AclCache aclCache,
        AclAuthorizationStrategy aclAuthorizationStrategy,
        PermissionGrantingStrategy permissionGrantingStrategy,
        PermissionFactory permissionFactory) {
        
        BasicLookupStrategy lookupStrategy = new BasicLookupStrategy(
            dataSource,
            aclCache,
            aclAuthorizationStrategy,
            permissionGrantingStrategy
        );

        lookupStrategy.setPermissionFactory(permissionFactory);
        
        return lookupStrategy;
    }

    @Bean
    public AclCache aclCache(PermissionGrantingStrategy permissionGrantingStrategy, AclAuthorizationStrategy aclAuthorizationStrategy) {
        return new SpringCacheBasedAclCache(
            new NoOpCache("myCache"),
            permissionGrantingStrategy,
            aclAuthorizationStrategy
        );
    }
    
    @Bean
    public AclAuthorizationStrategy aclAuthorizationStrategy() {
        return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("PERMISSION_MANAGER"));
    }

    @Bean
    public PermissionGrantingStrategy permissionGrantingStrategy() {
        return new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger());
    }
}
