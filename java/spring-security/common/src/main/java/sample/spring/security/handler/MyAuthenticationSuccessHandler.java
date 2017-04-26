package sample.spring.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                throws IOException, ServletException {
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, "/hello.html");
    }
}
