<%--
  Created by IntelliJ IDEA.
  User: Naku
  Date: 19/03/2023
  Time: 02:26
  To change this template use File | Settings | File Templates.
--%>
<%
if (session.getAttribute("userC")==null)
        response.sendRedirect("/HermesStore/login.jsp");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
