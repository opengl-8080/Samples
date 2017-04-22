<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8" />
        <title>Max Session</title>
    </head>
    <body>
        <h1>セッション数が上限に達しています</h1>
        
        <%@page import="org.springframework.security.core.AuthenticationException" %>
        <%@page import="org.springframework.security.web.WebAttributes" %>
        <%
            AuthenticationException e = (AuthenticationException)session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            pageContext.setAttribute("errorMessage", e.getMessage());
        %>
        
        <h2 style="color: red;">${errorMessage}</h2>
    </body>
</html>
