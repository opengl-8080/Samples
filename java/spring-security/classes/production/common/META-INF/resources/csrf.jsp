<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>CSRF</title>
    </head>
    <body>
        <h1>CSRF</h1>
        
        <c:url var="csrfUrl" value="/csrf" />
        <form action="${csrfUrl}" method="post">
            <input type="submit" value="Submit" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
    </body>
</html>
