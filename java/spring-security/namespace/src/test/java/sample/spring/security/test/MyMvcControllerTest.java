package sample.spring.security.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
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
    public void test() throws Exception {
        MvcResult result = this.mvc.perform(logout()).andReturn();
        this.printResponse("test", result);
    }

    private void printResponse(String method, MvcResult result) throws Exception {
        MockHttpServletResponse response = result.getResponse();
        int status = response.getStatus();
        String location = response.getHeader("Location");

        System.out.println("[" + method + "]\n" +
                "status : " + status + "\n" +
                "location : " + location);
    }
}
