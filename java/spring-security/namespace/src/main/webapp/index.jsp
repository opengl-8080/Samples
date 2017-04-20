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
        <%
        org.springframework.security.core.Authentication auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.UserDetails detauls =
            (org.springframework.security.core.userdetails.UserDetails)auth.getPrincipal();
            Object name = detauls.getUsername();
        %>
        
        name = <%=name%>
        
        <c:url var="logoutUrl" value="/logout" />
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="logout" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
    </body>
</html>