package sample.spring.security.servlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import sample.spring.security.service.MyRunAsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/run-as/*")
public class RunAsTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(req.getServletContext());
        MyRunAsService service = context.getBean(MyRunAsService.class);

        service.printAuthorities("メソッド呼び出し前");
        
        if ("/secured".equals(req.getPathInfo())) {
            service.secured();
        } else {
            service.nonSecured();
        }

        service.printAuthorities("メソッド呼び出し後");
    }
}
