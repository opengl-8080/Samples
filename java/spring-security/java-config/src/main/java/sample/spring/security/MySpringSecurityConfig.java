package sample.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.intercept.RunAsImplAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import sample.spring.security.service.MyRunAsService;

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
        
        auth.authenticationProvider(provider)
            .inMemoryAuthentication()
                .withUser("hoge")
                .password("hoge")
                .authorities("ROLE_USER");
    }
    
    @Bean
    public MyRunAsService myRunAsService() {
        return new MyRunAsService();
    }
}
