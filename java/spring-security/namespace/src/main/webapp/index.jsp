<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Hello Spring Security!!</title>
    </head>
    <body>
        <h1>Hello Spring Security!!</h1>
        
        <%@page import="org.springframework.security.core.context.SecurityContextHolder" %>
        <%@page import="org.springframework.security.core.Authentication" %>
        <%@page import="org.springframework.security.core.userdetails.UserDetails" %>
        <%
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetails detauls = (UserDetails)auth.getPrincipal();
            Object name = detauls.getUsername();
        %>
        
        username = <%=name%>
        
        <c:url var="logoutUrl" value="/logout" />
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="logout" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
    </body>
</html>