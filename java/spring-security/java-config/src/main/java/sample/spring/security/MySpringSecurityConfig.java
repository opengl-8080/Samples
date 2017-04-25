package sample.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.Collections;

@EnableWebSecurity
public class MySpringSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers("/my-login.jsp").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/my-login.jsp");
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("hoge")
                .password("hoge")
                .authorities(Collections.emptyList());
    }
}
