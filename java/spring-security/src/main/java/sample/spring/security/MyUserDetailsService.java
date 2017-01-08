package sample.spring.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.List;

public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!"test".equals(username) && !"admin".equals(username)) {
            throw new UsernameNotFoundException("無効なユーザーです");
        }

        String password = "$2a$08$BEGKzsHbIjbdk6i3S5eB.u/wgKPtzIWA45SwmZwRvrpTqmWPdVUu2";
        
        List<GrantedAuthority> authorities =
            "admin".equals(username)
                ? AuthorityUtils.createAuthorityList("ROLE_ADMIN")
                : Collections.emptyList();
        
        return new User(username, password, authorities);
    }
}
