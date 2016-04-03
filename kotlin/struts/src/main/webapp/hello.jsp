<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<html:html>
  <head>
    <title>Struts &amp; Kotlin</title>
  </head>
  <body>
    <html:form action="/hello" method="post" >
      message : <html:text property="message"/>
      <html:submit value="submit"/>
    </html:form>
  </body>
</html:html>
