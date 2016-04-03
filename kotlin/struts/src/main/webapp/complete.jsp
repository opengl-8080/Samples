<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<html>
  <head>
    <title>Struts &amp; Kotlin</title>
  </head>
  <body>
    <h1>
      <bean:write name="helloForm" property="message" />
    </h1>
  </body>
</html>
