package sample.spring.security.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-applicationContext.xml")
@WebAppConfiguration
public class MyMvcControllerTest {
    private static final String USERNAME = "foo";
    private static final String PASSWORD = "test-pass";
    private static final List<GrantedAuthority> AUTHORITIES = AuthorityUtils.createAuthorityList("FOO", "BAR");
    
    @Autowired
    private WebApplicationContext context;
    
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        this.mvc = MockMvcBuilders
                    .webAppContextSetup(this.context)
                    .apply(SecurityMockMvcConfigurers.springSecurity())
                    .build();
    }

    @Test
    public void withUser() throws Exception {
        System.out.println("[withUser]");
        this.mvc.perform(
            get("/mvc").with(user(USERNAME))
        );
    }

    @Test
    public void customized() throws Exception {
        System.out.println("[customized]");
        this.mvc.perform(
            get("/mvc").with(
                user(USERNAME)
                .password(PASSWORD)
                .authorities(AUTHORITIES)
            )
        );
    }

    @Test
    public void userDetails() throws Exception {
        System.out.println("[userDetails]");
        UserDetails user = this.createUserDetails();

        this.mvc.perform(
            get("/mvc").with(user(user))
        );
    }

    @Test
    public void withAnonymous() throws Exception {
        System.out.println("[withAnonymous]");

        this.mvc.perform(
            get("/mvc").with(anonymous())
        );
    }

    @Test
    public void withAuthentication() throws Exception {
        System.out.println("[withAuthentication]");
        Authentication auth = this.createAuthentication();

        this.mvc.perform(
            get("/mvc").with(authentication(auth))
        );
    }

    @Test
    public void withSecurityContext() throws Exception {
        System.out.println("[withSecurityContext]");
        Authentication auth = this.createAuthentication();
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);

        this.mvc.perform(
            get("/mvc").with(securityContext(context))
        );
    }

    private UserDetails createUserDetails() {
        return new User(USERNAME, PASSWORD, AUTHORITIES);
    }
    
    private Authentication createAuthentication() {
        UserDetails user = this.createUserDetails();
        return new UsernamePasswordAuthenticationToken(user, user.getPassword());
    }
}
