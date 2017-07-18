package sample.spring.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

public class MyUserDetailsService implements UserDetailsService {
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new MyUser(username, "test-password", AuthorityUtils.createAuthorityList("FOO", "BAR"));
    }
    
    private static class MyUser extends User {
        private MyUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
            super(username, password, authorities);
        }
    }
}
