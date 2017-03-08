package sample.spring.security.voter;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AcceptFugaVoter implements AccessDecisionVoter<Object> {
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserDetails)) {
            return ACCESS_ABSTAIN;
        }

        String username = ((UserDetails)principal).getUsername();
        return "fuga".equals(username) ? ACCESS_GRANTED : ACCESS_DENIED;
    }
}
