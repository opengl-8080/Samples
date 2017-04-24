package sample.spring.security.servlet;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/encode-password")
public class EncodePasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(req.getServletContext());
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);

        String password = req.getParameter("password");
        String encode = passwordEncoder.encode(password);
        System.out.println(encode);
    }
}
