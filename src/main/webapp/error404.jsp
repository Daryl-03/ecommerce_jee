<%--
  Created by IntelliJ IDEA.
  User: Naku
  Date: 21/03/2023
  Time: 09:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Erreur</title>
    <jsp:include page="/css.jsp" />
</head>
<body>
   <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="text-center">
                    <img src="${pageContext.request.contextPath}/include/images/404.svg" alt="Erreur 404">
                    <h1 class="display-4 mt-3">Erreur 404</h1>
                    <p class="lead">Désolé, la page que vous recherchez est introuvable.</p>
                    <p>Veuillez vérifier l'URL ou retourner à la <a href="/HermesStore/login.jsp">page d'accueil</a>.</p>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="/js.jsp" />
</body>
</html>
