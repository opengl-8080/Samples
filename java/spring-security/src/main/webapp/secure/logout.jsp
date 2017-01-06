<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>/secure/logout.jsp</title>
    </head>
    <body>
        <h1>/secure/logout.jsp</h1>
        
        <c:url var="logoutUrl" value="/logout" />
        <form action="${logoutUrl}" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="submit" value="Logout" />
        </form>
    </body>
</html>