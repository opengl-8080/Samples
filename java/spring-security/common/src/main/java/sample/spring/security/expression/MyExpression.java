package sample.spring.security.expression;

import org.springframework.security.core.Authentication;

public class MyExpression {

    public boolean check(Authentication authentication) {
        String name = authentication.getName();
        System.out.println("name = " + name);
        return "hoge".equals(name);
    }
}
