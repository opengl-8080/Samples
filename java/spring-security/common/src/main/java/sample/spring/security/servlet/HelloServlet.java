package sample.spring.security.servlet;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import sample.spring.security.service.HelloService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String strategy = SecurityContextHolder.getContextHolderStrategy().getClass().getSimpleName();
        System.out.println("[" + strategy + "]");
        this.printAuthentication();
        
        Thread thread = new Thread(this::printAuthentication);
        thread.setName("sub-thread");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void printAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.printf("[%s] auth.hash=%s%n", Thread.currentThread().getName(), auth.hashCode());
    }
}
