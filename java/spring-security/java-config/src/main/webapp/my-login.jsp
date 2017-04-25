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
        
        <c:url var="loginUrl" value="/login" />
        <form action="${loginUrl}" method="post">
            <div>
                <label>ユーザー名: <input type="text" name="username" /></label>
            </div>
            <div>
                <label>パスワード: <input type="password" name="password" /></label>
            </div>
            <input type="submit" value="login" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
    </body>
</html>
