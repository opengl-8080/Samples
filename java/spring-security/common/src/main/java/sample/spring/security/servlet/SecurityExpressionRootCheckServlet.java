package sample.spring.security.servlet;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/expression")
public class SecurityExpressionRootCheckServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("isAnonymous = " + this.isAnonymous());
        System.out.println("isRememberMe = " + this.isRememberMe());
        System.out.println("isAuthenticated = " + this.isAuthenticated());
        System.out.println("isFullyAuthenticated = " + this.isFullyAuthenticated());
    }
    
    private boolean isAnonymous() {
        return this.getResolver().isAnonymous(this.getAuthentication());
    }

    private boolean isRememberMe() {
        return this.getResolver().isRememberMe(this.getAuthentication());
    }

    private boolean isAuthenticated() {
        return !isAnonymous();
    }

    private boolean isFullyAuthenticated() {
        return !this.getResolver().isAnonymous(this.getAuthentication())
                && !this.getResolver().isRememberMe(this.getAuthentication());
    }
    
    private AuthenticationTrustResolver getResolver() {
        return new AuthenticationTrustResolverImpl();
    }
    
    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
