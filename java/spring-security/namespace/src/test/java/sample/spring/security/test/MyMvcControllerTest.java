package sample.spring.security.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-applicationContext.xml")
@WebAppConfiguration
public class MyMvcControllerTest {
    @Autowired
    private WebApplicationContext context;
    
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        this.mvc = MockMvcBuilders
                    .webAppContextSetup(this.context)
                    .apply(springSecurity())
                    .build();
    }

    @Test
    public void test_unauthenticated() throws Exception {
        this.mvc.perform(formLogin().user("foo").password("invalid"))
                .andExpect(unauthenticated());
    }

    @Test
    public void test_authenticated() throws Exception {
        this.mvc.perform(formLogin().user("foo").password("foo"))
                .andExpect(authenticated());
    }

    @Test
    public void test_withUsername() throws Exception {
        this.mvc.perform(formLogin().user("foo").password("foo"))
                .andExpect(authenticated().withUsername("foo"));
    }

    @Test
    public void test_withAuthorities() throws Exception {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("FOO", "BAR");
        
        this.mvc.perform(formLogin().user("foo").password("foo"))
                .andExpect(authenticated().withAuthorities(authorities));
    }

    @Test
    public void test_combine() throws Exception {
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("FOO", "BAR");
        
        this.mvc.perform(formLogin().user("foo").password("foo"))
                .andExpect(authenticated().withUsername("foo").withAuthorities(authorities));
    }

    @Test
    public void test_withRoles() throws Exception {
        this.mvc.perform(formLogin().user("fizz").password("fizz"))
                .andExpect(authenticated().withRoles("FIZZ", "BUZZ"));
    }
}
