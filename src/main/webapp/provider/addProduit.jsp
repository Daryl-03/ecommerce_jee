        <%@ page import="java.util.List" %>
<%@ page import="com.hermesstore.projetexamen2021.model.Categorie" %>
<%@ page import="com.hermesstore.projetexamen2021.model.datasource.CategorieDAO" %>
<%@ page import="com.hermesstore.projetexamen2021.model.Fournisseur" %>
<%@ page import="com.hermesstore.projetexamen2021.model.Utilisateur" %>
<%@ page import="com.hermesstore.projetexamen2021.model.datasource.ProduitDAO" %>
<%@ page import="com.hermesstore.projetexamen2021.model.Produit" %><%--
  Created by IntelliJ IDEA.
  User: Naku
  Date: 21/03/2023
  Time: 08:13
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
    List<Categorie> categories = new CategorieDAO().readAll();

    int id = 0;
    Produit produit = null;
    if (request.getParameter("id") != null) {
        id = Integer.parseInt(request.getParameter("id"));
        produit = new ProduitDAO().read(id);
        if(produit == null || produit.getIdFournisseur() != fournisseur.getId()) {
            response.sendRedirect("/HermesStore/provider");
            return;
        }
    }
%>
<html>
<head>
    <title>Ajouter un produit</title>
    <jsp:include page="/include/css.jsp"/>
    <link rel="stylesheet" href="../include/css/provider/providerIndex.css">
</head>
<body>
    <jsp:include page="header.jsp"/>

    <ul class="breadcrumb row pl-5">
        <li class="breadcrumb-item"><a href="index.jsp">Accueil</a></li>
        <li class="breadcrumb-item active" aria-current="page">
            <%=id != 0 ? "Mise à jour produit" : "Nouveau produit"%>
        </li>
    </ul>
    <main class="row">
        <div class="container-fluid h-100 w-100 vw-100 text-center">
            <div class="row h-100 w-100 d-flex align-items-center my-5">
              <div class="card custom-card-login shadow rounded col-md-5 col-lg-6 mx-auto">
                <div class="card-body">
                  <h1 class="h1 text-primary"><%=id != 0 ? "Modifier Produit" : "Ajout Produit"%></h1>
                  <form method="post" action=<%=id != 0 ? "produit/update" : "produit/add"%> enctype="multipart/form-data">
                    <input type="hidden" name="id" value="<%=id%>">
                    <br>
                    <div class="form-group row d-flex justify-content-between align-items-center">
                      <label class="col-3 col-form-label" for="nom">Nom :</label>
                      <input id="nom" name="nom" type="text" class="col-sm-3 col-md-8 form-control" placeholder="Nom" required value="<%=id != 0 ? produit.getNom():""%>" >
                    </div>
                    <br>
                    <div class="form-group row d-flex justify-content-between align-items-center">
                      <label class="col-3 col-form-label" for="image">Image :</label>
                      <input type="file" name="image" id="image" accept="image/*" >
                      <%
                          if (id != 0) {
                      %> <img src="../store/<%=produit.getImage()%>" alt="image" style="height: 5rem; width: 5rem;">
                        <%
                            }
                        %>
                    </div>
                    <br>
                    <div class="form-group row d-flex justify-content-between align-items-center">
                      <label class="col-3 col-form-label" for="categorie">Catégorie :</label>
                      <select id="categorie" name="categorie" class="col-sm-3 col-md-8 form-control" required>
                        <% for (Categorie categorie : categories) { %>
                            <option <%= id==0?( categorie.getNom().equals("Alimentation") ? "selected" : ""):( categorie.getNom().equals(produit.getCategorie().getNom()) ? "selected" : "")%> value="<%=categorie.getId()%>"><%=categorie.getNom()%></option>
                        <% } %>
                      </select>
                    </div>
                    <br>
                    <div class="form-group row d-flex justify-content-between align-items-center">
                      <label class="col-3 col-form-label" for="prixUn">Prix :</label>
                      <input min="0" id="prixUn" name="prixUn" type="number" class="col-sm-3 col-md-8 form-control" placeholder="Prix Unitaire" value="<%=id!=0?produit.getPrixUnitaire():""%>" required>
                    </div>
                    <br>
                    <div class="form-group row d-flex justify-content-between align-items-center">
                      <label class="col-3 col-form-label" for="qte">Quantité :</label>
                      <input min="1" id="qte" name="qte" type="number" class="col-sm-3 col-md-8 form-control" placeholder="Quantité" value="<%=id!=0?produit.getQuantite():""%>" required>
                    </div>
                    <br>
                    <div id="opt-group" style="<%=id!=0?(produit.getCategorie().getNom().equals("Alimentation")?"":"display: none!important;"):""%>">
                        <div class="form-group row d-md-flex justify-content-between align-items-center">
                          <label class="col-3 col-form-label" for="dateFab">Fabrication :</label>
                          <input id="dateFab" name="dateFab" type="date" class="col-sm-3 col-md-8 form-control" placeholder="Date de Fabrication" value="<%=id!=0?produit.getDateFabrication():""%>" <%=id!=0?(produit.getCategorie().getNom().equals("Alimentation")?"required":""):"required"%>>
                          <br>
                        </div>
                        <div class="form-group row d-md-flex justify-content-between align-items-center">
                            <label class="col-3 col-form-label" for="dateExp">Expiration :</label>
                            <input id="dateExp" name="dateExp" type="date" class="col-sm-3 col-md-8 form-control" placeholder="Date d'expiration" value="<%=id!=0?produit.getDateExpiration():""%>" <%=id!=0?(produit.getCategorie().getNom().equals("Alimentation")?"required":""):"required"%>>
                            <br>
                        </div>
                    </div>
                    <div class="form-group">
                      <button type="submit" class="btn btn-primary"><%=id!=0?"Modifier":"Ajouter"%></button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </div>
    </main>
    <jsp:include page="footer.jsp"/>
    <jsp:include page="/include/js.jsp"/>
    <script>
        $(document).ready(function() {
            $('#categorie').change(function(){
              if($(this).val() != 'Alimentation'){
                // ajout dû style "display: none!important;" dans le div spécialité
                $('#opt-group').prop('style', 'display: none!important;');
                $('#opt-group input').removeAttr('required'); // retirer l'attribut "required" du champ spécialité
                $('#opt-group input').val(''); // vider le champ spécialité
              } else {
                // retirer dû style "display: none!important;" dans le div spécialité
                $('#opt-group').prop('style', '');
                $('#opt-group input').attr('required', true); // ajouter l'attribut "required" au champ spécialité
              }
            });
        });
    </script>
</body>
</html>
