package sample.spring.security.service;

import org.springframework.security.access.prepost.PreAuthorize;

public class MyMethodSecurityService {

    @PreAuthorize("#strValue == 'aaa' and #intValue == 1")
    public String getMessage(String strValue, int intValue) {
        return "strValue=" + strValue + ", intValue=" + intValue;
    }
}
