<%--
  Created by IntelliJ IDEA.
  User: Naku
  Date: 17/03/2023
  Time: 10:43
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
    <title>Inscription Client</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <jsp:include page="/include/css.jsp" />
    <link rel="stylesheet" href="../include/css/main/main.css">
</head>
<body class="bg-warning" >

    <div class="container-fluid h-100 w-100 vh- vw-100 text-center">
        <div class="row h-100 w-100 d-flex align-items-center my-5">
          <div class="card custom-card-login shadow rounded col-md-5 col-lg-6 mx-auto">
            <div class="card-body">
              <h1 class="h1 text-primary">Inscription Client</h1>
              <form method="post" action="Register/client">
                <br>
                <div class="form-group row d-flex justify-content-around align-items-center">
                  <label class="col-2 col-form-label" for="nom">Nom :</label>
                  <input id="nom" name="nom" type="text" class="col-sm-3 col-md-8 form-control" placeholder="Nom" required>
                </div>
                <br>
                <div class="form-group row d-flex justify-content-around align-items-center">
                  <label class="col-3 col-form-label" for="type">Type :</label>
                  <select id="type" name="type" class="col-sm-3 col-md-8 form-control" required>
                    <option value="particulier" selected>Particulier</option>
                    <option value="entreprise">Entreprise</option>
                  </select>
                </div>
                <br>
                <div class="form-group row d-flex justify-content-around align-items-center">
                  <label class="col-3 col-form-label" for="adresse">Adresse :</label>
                  <input id="adresse" name="adresse" type="text" class="col-sm-3 col-md-8 form-control" placeholder="Adresse" required>
                </div>
                <br>
                <div class="form-group row d-flex justify-content-around align-items-center">
                  <label class="col-3 col-form-label" for="tel">Téléphone :</label>
                  <input id="tel" name="tel" type="text" class="col-sm-3 col-md-8 form-control" placeholder="Téléphone" required>
                </div>
                <br>
                <div class="form-group row d-flex justify-content-around align-items-center">
                  <label class="col-3 col-form-label" for="email">Email :</label>
                  <input id="email" name="email" type="email" class="col-sm-3 col-md-8 form-control" placeholder="Adresse" required>
                </div>
                <br>
                <div class="form-group row d-flex justify-content-around align-items-center">
                  <label class="col-3 col-form-label" for="pwd">Mot de passe :</label>
                  <input id="pwd" name="pwd" type="password" class="col-sm-3 col-md-8 form-control" placeholder="Mot de passe" required>
                </div>
                <br>
                <div id="specialite-group" style="display: none!important;" class="form-group row d-md-flex justify-content-around align-items-center">
                  <label class="col-3 col-form-label" for="specialite">Spécialité :</label>
                  <input id="specialite" name="specialite" type="text" class="col-sm-3 col-md-8 form-control" placeholder="Spécialité">
                  <br>
                </div>
                <div class="form-group">
                  <button type="submit" class="btn btn-primary">S'inscrire</button>
                </div>
              </form>
              <p class="mt-3">Vous avez déjà un compte ? <a href="../login.jsp">Connectez-vous</a></p>
              <p class="mt-3">Fournisseur ? <a href="../provider/register.jsp">Inscrivez-vous ici</a></p>
            </div>
          </div>
        </div>
      </div>

    <jsp:include page="/include/js.jsp" />
    <script>
        $(document).ready(function() {
            $('#type').change(function(){
              if($(this).val() == 'particulier'){
                // ajout du style "display: none!important;" dans le div spécialité
                $('#specialite-group').prop('style', 'display: none!important;');
                $('#specialite-group input').removeAttr('required'); // retirer l'attribut "required" du champ spécialité
                $('#specialite-group input').val(''); // vider le champ spécialité
              } else if($(this).val() == 'entreprise'){
                // retirer du style "display: none!important;" dans le div spécialité
                $('#specialite-group').prop('style', '');
                $('#specialite-group input').attr('required', true); // ajouter l'attribut "required" au champ spécialité
              }
            });
          });
    </script>
</body>
</html>
