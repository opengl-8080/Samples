package sample.spring.security.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/cors")
public class CorsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("CorsServlet");
        
        resp.setHeader("Original-Header", "CORS!!");
        try (PrintWriter writer = resp.getWriter()) {
            writer.println("Hello CORS!!");
        }
    }
}
