<%@ page import="com.hermesstore.projetexamen2021.model.Utilisateur" %>
<%@ page import="com.hermesstore.projetexamen2021.model.Fournisseur" %><%--
  Created by IntelliJ IDEA.
  User: Naku
  Date: 22/03/2023
  Time: 19:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Utilisateur user = (Utilisateur) session.getAttribute("userC");
    if (user == null) {
        response.sendRedirect("/HermesStore/login.jsp");
        return;
    } else if (user.getProfil().equals("Client")) {
        response.sendRedirect("/HermesStore/client");
    }

    Fournisseur fournisseur = (Fournisseur) user;

%>
<html>
<head>
    <title>Accueil Fournisseur</title>
    <jsp:include page="/include/css.jsp"/>
    <link rel="stylesheet" href="../include/css/provider/providerIndex.css">
</head>
<body>
<jsp:include page="header.jsp"/>

<ul class="breadcrumb row mx-0 px-3">
    <li class="breadcrumb-item"><a href="index.jsp">Accueil</a></li>
    <li class="breadcrumb-item active" aria-current="page">Profil</li>
</ul>
<main class="row pl-2">
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <h2>Mes informations</h2>
                <form method="post" action="Profil/provider" >
                    <div class="form-group">
                        <label for="nom">Nom</label>
                        <input type="text" name="nom" class="form-control" id="nom" value="<%=fournisseur.getNom()%>">
                    </div>
                    <div class="form-group row d-flex justify-content-around align-items-center">
                        <label class="col-3 col-form-label" for="nationalite">Nationalité :</label>
                        <jsp:include page="nationalites.jsp"/>
                    </div>
                    <div class="form-group">
                        <label for="adresse">Adresse</label>
                        <input type="text" class="form-control" name="adresse" id="adresse"
                               value="<%=fournisseur.getAdresse()%>">
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" name="email" class="form-control" id="email" value="<%=fournisseur.getLogin()%>">
                    </div>
                    <div class="form-group">
                        <label for="tel">Téléphone</label>
                        <input type="tel" name="tel" class="form-control" id="tel" value="<%=fournisseur.getTelephone()%>">
                    </div>
                    <button type="submit" class="btn btn-primary">Enregistrer</button>
                </form>
            </div>
            <div class="col-md-6">
                <h2>Modifier le mot de passe</h2>
                <form method="post" action="Password/provider" >
                    <div class="form-group">
                        <label for="currentPassword">Mot de passe actuel</label>
                        <input type="password" name="currentPassword" class="form-control" id="currentPassword">
                    </div>
                    <div class="form-group">
                        <label for="newPassword">Nouveau mot de passe</label>
                        <input type="password" class="form-control" name="newPassword" id="newPassword">
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword">Confirmer le nouveau mot de passe</label>
                        <input type="password" class="form-control" name="confirmPassword" id="confirmPassword">
                    </div>
                    <button type="submit" class="btn btn-primary">Enregistrer</button>
                </form>
            </div>
        </div>
    </div>

</main>

    <jsp:include page="footer.jsp"/>
    <jsp:include page="/include/js.jsp"/>
    <%

    System.out.println("session : " + session.getAttribute("success"));
    if (session.getAttribute("success") != null) {
        String success = (String) session.getAttribute("success");
        System.out.println(success);
        out.println("<script>toastr.success('" + success + "')</script>");
        session.removeAttribute("success");
    } else if (session.getAttribute("error") != null) {
        String error = (String) session.getAttribute("error");
        System.out.println(error);
        out.println("<script>toastr.error('" + error + "')</script>");
        session.removeAttribute("error");
    }
%>
    <script>
        $(document).ready(function () {
            $("#nationalite option[value='<%=fournisseur.getNationalite()%>']").attr("selected", true);
        });
    </script>
</body>
</html>
