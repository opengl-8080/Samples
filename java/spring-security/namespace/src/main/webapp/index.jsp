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
        
        <c:url var="logoutUrl" value="/logout" />
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="logout" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
        
        <c:url var="csrfTest" value="/csrf/first" />
        <form action="${csrfTest}" method="post">
            <input type="submit" value="csrf" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
        
        <script
          src="https://code.jquery.com/jquery-3.2.1.min.js"
          integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
          crossorigin="anonymous"></script>
          
        <c:url var="jsDir" value="/js" />
        <script src="${jsDir}/index.js"></script>
    </body>
</html>