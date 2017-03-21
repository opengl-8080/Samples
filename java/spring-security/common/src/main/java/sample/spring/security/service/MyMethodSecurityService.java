package sample.spring.security.service;

import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class MyMethodSecurityService {
    
    @PreAuthorize("#strValue == 'aaa' and #intValue == 1")
    public String getMessage(@P("strValue") String strValue, @P("intValue") int intValue) {
        return "strValue=" + strValue + ", intValue=" + intValue;
    }
}
