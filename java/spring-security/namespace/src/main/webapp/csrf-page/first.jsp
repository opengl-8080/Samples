<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>CSRF First</title>
    </head>
    <body>
        <h1>CSRF First</h1>
        
        <c:url var="csrfTest" value="/csrf/second" />
        <form action="${csrfTest}" method="post">
            <input type="submit" value="csrf" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
    </body>
</html>
