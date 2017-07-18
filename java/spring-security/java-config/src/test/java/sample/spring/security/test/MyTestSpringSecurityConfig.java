package sample.spring.security.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class MyTestSpringSecurityConfig {
    
    @Bean
    public MyTestService myTestService() {
        return new MyTestService();
    }
}
