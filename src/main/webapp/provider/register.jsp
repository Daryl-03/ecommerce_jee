<%--
  Created by IntelliJ IDEA.
  User: Naku
  Date: 20/03/2023
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("userC")!=null)
        response.sendRedirect("index.jsp");
%>
<!doctype html>
<html lang="fr">
<head>
    <title>Inscription Fournisseur</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <jsp:include page="/include/css.jsp"/>
    <link rel="stylesheet" href="/include/css/main/main.css">
</head>
<body class="bg-warning">

<div class="container-fluid h-100 w-100 vw-100 text-center">
    <div class="row h-100 w-100 d-flex align-items-center my-5">
        <div class="card custom-card-login shadow rounded col-md-5 col-lg-6 mx-auto">
            <div class="card-body">
                <h1 class="h1 text-primary">Inscription Fournisseur</h1>
                <form method="post" action="Register/provider" >
                    <br>
                    <div class="form-group row d-flex justify-content-around align-items-center">
                        <label class="col-2 col-form-label" for="nom">Nom :</label>
                        <input id="nom" name="nom" type="text" class="col-sm-3 col-md-8 form-control" placeholder="Nom"
                               required>
                    </div>
                    <br>
                    <div class="form-group row d-flex justify-content-around align-items-center">
                        <label class="col-3 col-form-label" for="nationalite">Nationalité :</label>
                        <jsp:include page="nationalites.jsp"/>
                    </div>
                    <br>
                    <div class="form-group row d-flex justify-content-around align-items-center">
                        <label class="col-3 col-form-label" for="adresse">Adresse :</label>
                        <input id="adresse" name="adresse" type="text" class="col-sm-3 col-md-8 form-control"
                               placeholder="Adresse" required>
                    </div>
                    <br>
                    <div class="form-group row d-flex justify-content-around align-items-center">
                        <label class="col-3 col-form-label" for="tel">Téléphone :</label>
                        <input id="tel" name="tel" type="text" class="col-sm-3 col-md-8 form-control"
                               placeholder="Téléphone" required>
                    </div>
                    <br>
                    <div class="form-group row d-flex justify-content-around align-items-center">
                        <label class="col-3 col-form-label" for="email">Email :</label>
                        <input id="email" name="email" type="text" class="col-sm-3 col-md-8 form-control"
                               placeholder="Adresse" required>
                    </div>
                    <br>
                    <div class="form-group row d-flex justify-content-around align-items-center">
                        <label class="col-3 col-form-label" for="pwd">Mot de passe :</label>
                        <input id="pwd" name="pwd" type="password" class="col-sm-3 col-md-8 form-control"
                               placeholder="Mot de passe" required>
                    </div>
                    <br>
                    <div class="form-group">
                        <br>
                        <button type="submit" class="btn btn-primary">S'inscrire</button>
                    </div>
                </form>
                <p class="mt-3">Vous avez déjà un compte ? <a href="../login.jsp">Connectez-vous</a></p>
                <p class="mt-3">Client ? <a href="../client/register.jsp">Inscrivez-vous ici</a></p>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/include/js.jsp"/>
</body>
</html>
