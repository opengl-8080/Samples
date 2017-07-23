package sample.spring.security.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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
    @WithMockUser(username="foo", password="test-pass", authorities={"FOO", "BAR"})
    public void test() throws Exception {
        this.mvc.perform(get("/mvc"));
    }
}
