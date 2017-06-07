package sample.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.acls.domain.AclAuthorizationStrategyImpl;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.domain.DefaultPermissionGrantingStrategy;
import org.springframework.security.acls.domain.SpringCacheBasedAclCache;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import sample.spring.security.service.MyAclSampleService;

import javax.sql.DataSource;

@EnableWebSecurity
@EnableTransactionManagement
public class MySpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin();
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                    .generateUniqueName(true)
                    .setType(EmbeddedDatabaseType.H2)
                    .setScriptEncoding("UTF-8")
                    .addScript("/sql/create_acl_tables.sql")
                    .build();
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public AclCache aclCache() {
        return new SpringCacheBasedAclCache(
            new ConcurrentMapCache("test"),
            new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger()),
            new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ACL_ADMIN"))
        );
    }

    @Bean
    @Autowired
    public LookupStrategy lookupStrategy(
        DataSource dataSource,
        AclCache aclCache
    ) {
        return new BasicLookupStrategy(
            dataSource,
            aclCache,
            new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR")),
            new ConsoleAuditLogger()
        );
    }

    @Bean
    @Autowired
    public MutableAclService aclService(
        DataSource dataSource,
        AclCache aclCache,
        LookupStrategy lookupStrategy
    ) {
        return new JdbcMutableAclService(
            dataSource,
            lookupStrategy,
            aclCache
        );
    }

    @Bean
    @Autowired
    public MyAclSampleService myAclSampleService(
        MutableAclService aclService,
        DataSource dataSource
    ) {
        MyAclSampleService service = new MyAclSampleService();
//        service.setAclService(aclService);
//        service.setJdbcTemplate(new JdbcTemplate(dataSource));

        return service;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(false)
            .inMemoryAuthentication()
                .withUser("hoge")
                .password("hoge")
                .roles();
    }
}
