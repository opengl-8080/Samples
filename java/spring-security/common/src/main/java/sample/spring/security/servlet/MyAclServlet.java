package sample.spring.security.servlet;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import sample.spring.security.domain.Foo;
import sample.spring.security.service.MyAclSampleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/acl")
public class MyAclServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.printPrincipal();
        this.callServiceLogic(new Foo(44L), req);
        this.callServiceLogic(new Foo(45L), req);
    }

    private void printPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        System.out.println("name=" + name);
        System.out.println("authorities=" +
                auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(", "))
        );
    }
    
    private void callServiceLogic(Foo foo, HttpServletRequest req) {
        try {
            this.findServiceBean(req).logic(foo);
        } catch (AccessDeniedException e) {
            System.out.println("AccessDeniedException : " + e.getMessage());
        }
    }
    
    private MyAclSampleService findServiceBean(HttpServletRequest req) {
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(req.getServletContext());
        return context.getBean(MyAclSampleService.class);
    }
}
