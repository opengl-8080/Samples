<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>
    <head>
        <meta charset="utf-8" />
        <title>Reflected XSS</title>
    </head>
    <body>
        <h1>Reflected XSS</h1>
        
        ${param["q"]}
    </body>
</html>
