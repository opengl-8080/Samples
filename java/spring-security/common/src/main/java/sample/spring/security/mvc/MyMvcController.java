package sample.spring.security.mvc;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyMvcController {
    
    @GetMapping("/csrf")
    public String foo(CsrfToken token) {
        System.out.println("token=" + token.getToken() + ", headerName=" + token.getHeaderName() + ", parameterName=" + token.getParameterName());
        return "CSRF!!";
    }
}
