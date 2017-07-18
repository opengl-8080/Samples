package sample.spring.security.test;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class MyTestUserFactory implements WithSecurityContextFactory<MyTestUser> {
    
    @Override
    public SecurityContext createSecurityContext(MyTestUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        String name = annotation.name();
        String pass = annotation.pass();
        String authority = annotation.authority();

        UserDetails user = new User(name, pass, AuthorityUtils.createAuthorityList(authority));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                            user, user.getPassword(), user.getAuthorities());
        
        context.setAuthentication(authentication);

        return context;
    }
}
