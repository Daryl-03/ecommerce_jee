<%--
  Created by IntelliJ IDEA.
  User: Naku
  Date: 23/03/2023
  Time: 01:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.hermesstore.projetexamen2021.model.Utilisateur" %>
<%@ page import="com.hermesstore.projetexamen2021.model.Fournisseur" %>
<%@ page import="com.hermesstore.projetexamen2021.model.Client" %>
<%
    Utilisateur user = (Utilisateur) session.getAttribute("userC");
    if (user == null) {
        response.sendRedirect("/HermesStore/login.jsp");
        return;
    } else if (user.getProfil().equals("Fournisseur")) {
        response.sendRedirect("/HermesStore/provider/");
    }

    Client client = (Client) session.getAttribute("userC");

%>
<html>
<head>
    <title>Accueil Fournisseur</title>
    <jsp:include page="/include/css.jsp"/>
    <link rel="stylesheet" href="../include/css/provider/providerIndex.css">
</head>
<body>
<jsp:include page="header.jsp"/>

<ul class="breadcrumb row">
    <li class="breadcrumb-item"><a href="index.jsp">Accueil</a></li>
    <li class="breadcrumb-item active" aria-current="page">Profil</li>
</ul>
<main class="row pl-2">
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <h2>Mes informations</h2>
                <form method="post" action="Profil/client">
                    <div class="form-group">
                        <label for="nom">Nom</label>
                        <input type="text" name="nom" class="form-control" id="nom" value="<%=client.getNom()%>">
                    </div>
                    <div class="form-group row d-flex justify-content-around align-items-center">
                        <label class="col-3 col-form-label" for="type">Type :</label>
                        <select id="type" name="type" class="col-sm-3 col-md-8 form-control" required>
                            <option value="particulier" <%=client.getType().equals("particulier")?"selected":""%>>Particulier</option>
                            <option value="entreprise" <%=client.getType().equals("entreprise")?"selected":""%> >Entreprise</option>
                        </select>
                    </div>
                    <div id="specialite-group" style="<%=client.getType().equals("particulier")?"display: none!important;":""%>"
                         class="form-group row d-md-flex justify-content-around align-items-center">
                        <label class="col-3 col-form-label" for="specialite">Spécialité :</label>
                        <input id="specialite" name="specialite" type="text" class="col-sm-3 col-md-8 form-control"
                               placeholder="Spécialité">
                        <br>
                    </div>
                    <div class="form-group">
                        <label for="adresse">Adresse</label>
                        <input type="text" class="form-control" name="adresse" id="adresse"
                               value="<%=client.getAdresse()%>">
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" name="email" class="form-control" id="email" value="<%=client.getLogin()%>">
                    </div>
                    <div class="form-group">
                        <label for="tel">Téléphone</label>
                        <input type="tel" name="tel" class="form-control" id="tel" value="<%=client.getTelephone()%>">
                    </div>
                    <button type="submit" class="btn btn-primary">Enregistrer</button>
                </form>
            </div>
            <div class="col-md-6">
                <h2>Modifier le mot de passe</h2>
                <form method="post" action="Password/client" >
                    <div class="form-group">
                        <label for="currentPassword">Mot de passe actuel</label>
                        <input required type="password" name="currentPassword" class="form-control" id="currentPassword">
                    </div>
                    <div class="form-group">
                        <label for="newPassword">Nouveau mot de passe</label>
                        <input required type="password" class="form-control" name="newPassword" id="newPassword">
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword">Confirmer le nouveau mot de passe</label>
                        <input required type="password" class="form-control" name="confirmPassword" id="confirmPassword">
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


    if (session.getAttribute("success") != null) {
        String success = (String) session.getAttribute("success");

        out.println("<script>toastr.success('" + success + "')</script>");
        session.removeAttribute("success");
    } else if (session.getAttribute("error") != null) {
        String error = (String) session.getAttribute("error");

        out.println("<script>toastr.error('" + error + "')</script>");
        session.removeAttribute("error");
    }
%>
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
