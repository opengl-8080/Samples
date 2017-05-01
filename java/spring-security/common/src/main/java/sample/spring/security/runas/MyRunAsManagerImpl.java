package sample.spring.security.runas;

import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.intercept.RunAsManagerImpl;
import org.springframework.security.access.intercept.RunAsUserToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyRunAsManagerImpl extends RunAsManagerImpl {
    private static final Authentication NO_REPLACE = null;

    @Override
    public Authentication buildRunAs(Authentication authentication, Object secureObject, Collection<ConfigAttribute> attributes) {
        if (!this.supports(secureObject)) {
            return NO_REPLACE;
        }

        String[] runAsAuthorities = this.getRunAsAuthorities(secureObject);

        return new RunAsUserToken(
            this.getKey(),
            authentication.getPrincipal(),
            authentication.getCredentials(),
            this.createNewAuthorities(authentication, runAsAuthorities),
            authentication.getClass()
        );
    }
    
    private boolean supports(Object secureObject) {
        return (secureObject instanceof ReflectiveMethodInvocation)
                && ((ReflectiveMethodInvocation) secureObject).getMethod().isAnnotationPresent(RunAs.class);
    }
    
    private String[] getRunAsAuthorities(Object secureObject) {
        ReflectiveMethodInvocation invocation = (ReflectiveMethodInvocation) secureObject;
        Method method = invocation.getMethod();
        RunAs runAs = method.getAnnotation(RunAs.class);
        return runAs.value();
    }
    
    private List<GrantedAuthority> createNewAuthorities(Authentication authentication, String[] runAsAuthorities) {
        List<GrantedAuthority> newAuthorities = new ArrayList<>(authentication.getAuthorities());
        
        for (String runAsAuthority : runAsAuthorities) {
            newAuthorities.add(new SimpleGrantedAuthority(runAsAuthority));
        }
        
        return newAuthorities;
    }
}
