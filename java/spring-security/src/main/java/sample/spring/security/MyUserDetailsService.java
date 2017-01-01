package sample.spring.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Collections;

public class MyUserDetailsService implements UserDetailsService {
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!"test".equals(username)) {
            throw new UsernameNotFoundException("無効なユーザーです");
        }
        
        String password = "test";
        Collection<? extends GrantedAuthority> authorities = Collections.emptyList();
        
        return new User(username, password, authorities);
    }
}
