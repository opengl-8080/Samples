package sample.spring.security.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyAccessDeniedHandler implements AccessDeniedHandler {
    
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (!response.isCommitted()) {
            request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/access-denied.html");
            dispatcher.forward(request, response);
        }
    }
}
