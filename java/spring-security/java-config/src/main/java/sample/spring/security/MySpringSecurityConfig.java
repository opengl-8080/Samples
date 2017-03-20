package sample.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import sample.spring.security.voter.AcceptFugaVoter;

import java.util.Arrays;
import java.util.Collections;

@EnableWebSecurity
@ComponentScan
public class MySpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .accessDecisionManager(this.createAccessDecisionManager())
                .antMatchers("/login").permitAll()
                .anyRequest().hasAuthority("HOGE")
                .and()
                .formLogin();
    }
    
    private AccessDecisionManager createAccessDecisionManager() {
        return new AffirmativeBased(Arrays.asList(
            new WebExpressionVoter(),
            new AcceptFugaVoter()
        ));
    }
    
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("hoge")
            .password("hoge")
            .authorities("HOGE")
        .and()
            .withUser("fuga")
            .password("fuga")
            .authorities(Collections.emptyList())
        .and()
            .withUser("piyo")
            .password("piyo")
            .authorities(Collections.emptyList());
    }
}
