package sample.spring.security.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

public class MyUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("lock".equals(username)) {
            return new User(
                username,
                "test",
                true,
                true,
                true,
                false,
                Collections.emptyList()
            );
        } else if ("disable".equals(username)) {
            return new User(
                    username,
                    "test",
                    false,
                    true,
                    true,
                    true,
                    Collections.emptyList()
            );
        } else if ("credentials".equals(username)) {
            return new User(
                    username,
                    "test",
                    true,
                    true,
                    false,
                    true,
                    Collections.emptyList()
            );
        } else if ("account".equals(username)) {
            return new User(
                    username,
                    "test",
                    true,
                    false,
                    true,
                    true,
                    Collections.emptyList()
            );
        } else {
            return new User(
                    username,
                    "test",
                    true,
                    true,
                    true,
                    true,
                    Collections.emptyList()
            );
        }
    }
}
