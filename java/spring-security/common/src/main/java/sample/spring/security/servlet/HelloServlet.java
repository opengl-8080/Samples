package sample.spring.security.servlet;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void printAuthentication() {
        String threadName = Thread.currentThread().getName();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            System.out.printf("[%s] Authentication is null.%n", threadName);
        } else {
            System.out.printf("[%s] auth.hash = %s%n", threadName, auth.hashCode());
        }
    }
}
