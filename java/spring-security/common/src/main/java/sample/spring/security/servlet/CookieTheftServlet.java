package sample.spring.security.servlet;

import org.springframework.security.web.authentication.rememberme.CookieTheftException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/cookie-theft")
public class CookieTheftServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CookieTheftException e = (CookieTheftException)req.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        try (PrintWriter writer = resp.getWriter()) {
            writer.println(e.getMessage());
        }
    }
}