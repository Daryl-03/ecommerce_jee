<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<%--redirection vers la page de produits--%>
<jsp:forward page="login.jsp"></jsp:forward>
<br/>
<a href="hello-servlet">Hello Servlet</a>
</body>
</html>