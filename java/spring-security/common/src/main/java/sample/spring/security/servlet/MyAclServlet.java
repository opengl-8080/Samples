package sample.spring.security.servlet;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import sample.spring.security.service.MyAclSampleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/acl")
public class MyAclServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MyAclSampleService service = this.findServiceBean(req, MyAclSampleService.class);
        service.createObjectIdentity();
        this.printTables(req);
        
        service.findAcl();
    }
    
    private void printTables(HttpServletRequest req) {
        this.printTable(req, "ACL_SID");
        this.printTable(req, "ACL_CLASS");
        this.printTable(req, "ACL_OBJECT_IDENTITY");
        this.printTable(req, "ACL_ENTRY");
    }

    private void printTable(HttpServletRequest req, String table) {
        JdbcTemplate jdbcTemplate = this.findServiceBean(req, JdbcTemplate.class);
        List<Map<String, Object>> records = jdbcTemplate.queryForList("select * from " + table + " order by id asc");
        System.out.println("\n[" + table + "]");
        records.forEach(System.out::println);
    }

    private <T> T findServiceBean(HttpServletRequest req, Class<T> clazz) {
        WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(req.getServletContext());
        return context.getBean(clazz);
    }
}
