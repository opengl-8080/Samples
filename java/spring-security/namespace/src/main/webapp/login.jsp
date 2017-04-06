<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Login</title>
    </head>
    <body>
        <h1>Login</h1>
        
        <c:url var="loginUrl" value="/login" />
        <form id="form" action="${loginUrl}" method="post">
            <div>
                Username : <input type="text" name="username" />
            </div>
            <div>
                Password : <input type="password" name="password" />
            </div>
            
            <input type="button" id="loginButton" value="Login" />
            
            <input type="hidden" id="csrfToken" name="${_csrf.parameterName}" />
        </form>
        
        <c:url var="jsDirUrl" value="/js" />
        <script src="${jsDirUrl}/jquery.min.js"></script>
        <script src="${jsDirUrl}/login.js"></script>
    </body>
</html>
