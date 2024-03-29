<%@ page import="com.hermesstore.projetexamen2021.model.Utilisateur" %><%--
  Created by IntelliJ IDEA.
  User: Naku
  Date: 16/03/2023
  Time: 21:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
  Utilisateur user = (Utilisateur) session.getAttribute("userC");
    if (user != null) {
        if(user.getProfil().equals("Fournisseur")){
          response.sendRedirect("/HermesStore/provider");
          return;
        } else if(user.getProfil().equals("Client")){
          response.sendRedirect("/HermesStore/client");
          return;
        }

    }
  String error = (String) request.getAttribute("error");

%>

<html>
<head>
    <title>Connexion</title>
    <jsp:include page="css.jsp" />
    <link rel="stylesheet" href="include/css/main/main.css">
</head>
<body class="bg-warning" >

  <div class="container-fluid h-100 w-100 vh-100 vw-100 text-center">
    <div class="row h-100 w-100 d-flex align-items-center">
      <div class="card custom-card-login shadow rounded col-md-5 col-lg-6 mx-auto">
        <div class="card-body">
          <h1 class="h1 text-primary">Connexion</h1>
          <form method="post" action="LoginServlet">
            <br>
            <div class="form-group row d-flex justify-content-center align-items-center">
              <i class="bx bx-user col-1 icon-large text-primary" ></i>
              <input type="text" name="login" class="col-sm-3 col-md-8 form-control" placeholder="Login" required>
            </div>
            <br>
            <div class="form-group row d-flex justify-content-center align-items-center">
              <i class="bx bx-lock col-1 icon-large text-primary" ></i>
              <input type="password" name="password" class=" col-sm-3 col-md-8 form-control" placeholder="Mot de passe" required>
            </div>
            <div class="form-group">
            <div class="<%=error==null?"d-none":""%> alert alert-danger text-center" role="alert">
                <%=error==null?"":error%>
            </div>
              <br>
              <button type="submit" class="btn btn-primary">Se connecter</button>
            </div>
          </form>
          <p class="mt-3">Vous n'avez pas de compte ? <a href="client/register.jsp">Inscrivez-vous ici</a></p>
        </div>
      </div>
    </div>
  </div>


  <jsp:include page="js.jsp" />
</body>
</html>
