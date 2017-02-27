package sample.spring.security.servlet;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.stream.Collectors.*;

@WebServlet("/authentication")
public class SecurityContextHolderSampleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("[authorities]");
        System.out.println("  " + auth.getAuthorities().stream()
                                    .map(GrantedAuthority::getAuthority)
                                    .collect(joining("\n  ")));

        WebAuthenticationDetails details = (WebAuthenticationDetails) auth.getDetails();
        System.out.println("[details]");
        System.out.println("  IP Address : " + details.getRemoteAddress());
        System.out.println("  Session ID : " + details.getSessionId());

        UserDetails principal = (UserDetails) auth.getPrincipal();
        System.out.println("[principal]");
        System.out.println("  username : " + principal.getUsername());
        System.out.println("  password : " + principal.getPassword());

        System.out.println("[credentials]");
        System.out.println("  " + auth.getCredentials());
    }
}
