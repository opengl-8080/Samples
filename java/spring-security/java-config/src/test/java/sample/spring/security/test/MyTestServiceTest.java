package sample.spring.security.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=MyTestSpringSecurityConfig.class)
public class MyTestServiceTest {

    @Autowired
    private MyTestService service;

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void test_getMessage_no_authentication() throws Exception {
        // exercise
        this.service.getMessage();
    }

    @Test
    @WithMockUser(username = "hoge")
    public void test_getMessage() throws Exception {
        // exercise
        String message = this.service.getMessage();

        // verify
        Assert.assertEquals("Hello hoge", message);
    }
}