<%@ page contentType="text/html;charset=UTF-8" %>
<%@page import="org.jasig.cas.client.session.SingleSignOutHttpSessionListener"%>
<%@ page import="org.jasig.cas.client.authentication.AttributePrincipal" %>  
<html>
<body>
<h2>Hello World!</h2>
	wellcome to system!Dear 
<%  
 AttributePrincipal principal = (AttributePrincipal)request.getUserPrincipal();     
 String username = principal.getName();  
 out.println(username);
 %>!
<br>
<a href="http://localhost:8080/birp_web/index2.jsp">logout</a>  

</body>
</html>
