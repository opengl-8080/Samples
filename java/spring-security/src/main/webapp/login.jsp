<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Login</title>
    </head>
    <body>
        <h1>ログイン</h1>
        <c:url var="loginUrl" value="/my-login" />
        <form action="${loginUrl}" method="post">
            <div>
                username: <input name="loginId" type="text" />
            </div>
            <div>
                password: <input name="pass" type="password" />
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="submit" value="login" />
        </form>
    </body>
</html>