<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="org.jasig.cas.client.session.SingleSignOutHttpSessionListener"%>
<%@ page import="org.jasig.cas.client.authentication.AttributePrincipal" %>  
<html>
<body>
Dear 
<%
 AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();     
 String username = principal.getName();  
 out.println(username);
 %>! would you like logout?
<br>
<a href="http://localhost:8080/cas/logout">logout</a>
<%
 session.invalidate();
%>
</body>
</html>
