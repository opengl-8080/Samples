package sample.spring.security.test;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

public class MyMockUserPostProcessors {
    
    public static RequestPostProcessor hoge() {
        return user("hoge")
                .password("test-pass")
                .authorities(AuthorityUtils.createAuthorityList("FOO", "BAR"));
    }
}
