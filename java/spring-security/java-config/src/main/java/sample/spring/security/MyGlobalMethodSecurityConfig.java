package sample.spring.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.intercept.RunAsManager;
import org.springframework.security.access.intercept.RunAsManagerImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import sample.spring.security.runas.MyRunAsManagerImpl;

@Configuration
public class MyGlobalMethodSecurityConfig extends GlobalMethodSecurityConfiguration {
    
    public static final String RUN_AS_KEY = "foo";
    
    @Override
    protected RunAsManager runAsManager() {
        RunAsManagerImpl runAsManager = new MyRunAsManagerImpl();
        runAsManager.setKey(RUN_AS_KEY);
        return runAsManager;
    }
}
