package sample.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
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
        AcceptFugaVoter acceptFugaVoter = new AcceptFugaVoter();
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        AffirmativeBased accessDecisionManager = new AffirmativeBased(Arrays.asList(acceptFugaVoter, webExpressionVoter));

        http.authorizeRequests()
                .accessDecisionManager(accessDecisionManager)
                .antMatchers("/login").permitAll()
                .anyRequest().hasRole("USER")
                .and()
                .formLogin()
                .and()
                .rememberMe();
    }
    
    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("hoge")
            .password("hoge")
            .roles("USER")
        .and()
            .withUser("fuga")
            .password("fuga")
            .roles("FUGA")
        .and()
            .withUser("piyo")
            .password("piyo")
            .roles("PIYO");
    }
}
