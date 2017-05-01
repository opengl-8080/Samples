package sample.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import sample.spring.security.processor.MyMethodSecurityInterceptorInitializer;
import sample.spring.security.service.MyRunAsService;
import sample.spring.security.service.MyRunAsService2;

@EnableWebSecurity
@Import(MyGlobalMethodSecurityConfig.class)
public class MySpringSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        RunAsImplAuthenticationProvider provider = new RunAsImplAuthenticationProvider();
        provider.setKey(MyGlobalMethodSecurityConfig.RUN_AS_KEY);
        
        auth.eraseCredentials(false)
            .authenticationProvider(provider)
            .inMemoryAuthentication()
                .withUser("hoge")
                .password("hoge")
                .authorities("ROLE_USER");
    }
    
    @Bean
    public MyRunAsService myRunAsService(MyRunAsService2 service2) {
        return new MyRunAsService(service2);
    }
    
    @Bean
    public MyRunAsService2 myRunAsService2() {
        return new MyRunAsService2();
    }
    
    @Bean
    public MyMethodSecurityInterceptorInitializer myMethodSecurityInterceptorInitializer() {
        return new MyMethodSecurityInterceptorInitializer();
    }
}
