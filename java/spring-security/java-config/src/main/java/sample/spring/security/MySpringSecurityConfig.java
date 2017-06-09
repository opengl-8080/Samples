package sample.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import sample.spring.security.service.MyMethodSecurityService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
//@EnableTransactionManagement
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
    public MyMethodSecurityService myMethodSecurityService() {
        return new MyMethodSecurityService();
    }
    
    

//    @Bean
//    public DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder()
//                    .generateUniqueName(true)
//                    .setType(EmbeddedDatabaseType.H2)
//                    .setScriptEncoding("UTF-8")
//                    .addScript("/sql/create_acl_tables.sql")
//                    .build();
//    }
//
//    @Bean
//    @Autowired
//    public PlatformTransactionManager transactionManager(DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Bean
//    public AclCache aclCache() {
//        return new SpringCacheBasedAclCache(
//            new ConcurrentMapCache("test"),
//            new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger()),
//            new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ACL_ADMIN"))
//        );
//    }
//
//    @Bean
//    @Autowired
//    public LookupStrategy lookupStrategy(
//        DataSource dataSource,
//        AclCache aclCache
//    ) {
//        return new BasicLookupStrategy(
//            dataSource,
//            aclCache,
//            new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ADMINISTRATOR")),
//            new ConsoleAuditLogger()
//        );
//    }
//
//    @Bean
//    @Autowired
//    public MutableAclService aclService(
//        DataSource dataSource,
//        AclCache aclCache,
//        LookupStrategy lookupStrategy
//    ) {
//        return new JdbcMutableAclService(
//            dataSource,
//            lookupStrategy,
//            aclCache
//        );
//    }
//
//    @Bean
//    @Autowired
//    public MyAclSampleService myAclSampleService(
//        MutableAclService aclService,
//        DataSource dataSource
//    ) {
//        MyAclSampleService service = new MyAclSampleService();
////        service.setAclService(aclService);
////        service.setJdbcTemplate(new JdbcTemplate(dataSource));
//
//        return service;
//    }
//
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("user")
                .authorities("USER")
            .and()
                .withUser("admin")
                .password("admin")
                .authorities("ADMIN");
    }
}
