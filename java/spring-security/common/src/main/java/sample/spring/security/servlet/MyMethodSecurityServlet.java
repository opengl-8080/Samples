package sample.spring.security.servlet;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import sample.spring.security.service.MyMethodSecurityService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/method-security")
public class MyMethodSecurityServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(req.getServletContext());
        MyMethodSecurityService service = context.getBean(MyMethodSecurityService.class);

        String strValue = req.getParameter("strValue");
        int intValue = Integer.parseInt(req.getParameter("intValue"));

        PrintWriter writer = resp.getWriter();
        try {
            writer.println(service.getMessage(strValue, intValue));
        } catch (AccessDeniedException e) {
            writer.println(e.getMessage());
        } finally {
            writer.close();
        }
    }
}
