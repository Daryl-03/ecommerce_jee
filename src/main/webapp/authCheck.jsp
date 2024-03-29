
<%
    if (session.getAttribute("userC")==null)
        response.sendRedirect("/HermesStore/login.jsp");

        System.out.println(request.getSession().getAttribute("userC"));
%>