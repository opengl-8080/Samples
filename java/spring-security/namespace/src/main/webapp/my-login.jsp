<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>My Login Page</title>
    </head>
    <body>
        <h1>My Login Page</h1>
        
        <%@page import="org.springframework.security.core.AuthenticationException" %>
        <%@page import="org.springframework.security.web.WebAttributes" %>
        <%
        AuthenticationException e = (AuthenticationException)session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        if (e != null) {
            pageContext.setAttribute("message", e.getMessage());
        }
        %>
        
        <div style="color: red;">${message}</div>
        
        <c:url var="loginUrl" value="/login" />
        <form action="${loginUrl}" method="post">
            <div>
                <label>ユーザー名: <input type="text" name="username" /></label>
            </div>
            <div>
                <label>パスワード: <input type="password" name="password" /></label>
            </div>
            <div>
                <label>Remember-Me : <input type="checkbox" name="remember-me"></label>
            </div>
            <input type="submit" value="login" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
    </body>
</html>
