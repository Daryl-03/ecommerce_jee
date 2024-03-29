<%@ page import="com.hermesstore.projetexamen2021.model.*" %>
<%@ page import="com.hermesstore.projetexamen2021.model.datasource.LivraisonDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.hermesstore.projetexamen2021.model.datasource.CommandeDAO" %>
<%@ page import="com.hermesstore.projetexamen2021.model.datasource.ClientDAO" %><%--
  Created by IntelliJ IDEA.
  User: Naku
  Date: 23/03/2023
  Time: 23:09
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

    Fournisseur fournisseur = (Fournisseur) session.getAttribute("userC");
    List<Livraison> livraisons = new LivraisonDAO().readAllByFournisseur(fournisseur.getId());


%>
<html>
<head>
    <title>Livraisons</title>
    <jsp:include page="/include/css.jsp"/>
</head>
<body>
<jsp:include page="header.jsp"/>

<ul class="breadcrumb row mx-0 px-3">
    <li class="breadcrumb-item"><a href="index.jsp">Accueil</a></li>
    <li class="breadcrumb-item active" aria-current="page">Mes Livraisons</li>
</ul>
<div class="container row mx-auto">
    <h1>Mes Livraisons</h1>
    <hr>
    <table class="table table-hover">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">Date de Livraison</th>
            <th scope="col">D&eacute;tail</th>
            <th scope="col">Client</th>
            <th scope="col">Adresse</th>
            <th scope="col">Telephone</th>
            <th scope="col">Montant total</th>
            <th scope="col">Statut</th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody>
          <%
            int i = 0;
            for (Livraison livraison : livraisons) {
                Client client = new ClientDAO().read(new CommandeDAO().read(livraison.getId_cmd()).getId_client());
                double prix = 0;
                for (ProduitCmd produit : livraison.getProduits()) {
                    prix += produit.getQuantite() * produit.getProduit().getPrixUnitaire();
                }
          %>
              <tr>
                  <th scope="row"><%=++i%></th>
                  <td><%=livraison.getDate()==null?"A venir":livraison.getDate()%></td>
                  <td>
                  <%
                     List<ProduitCmd> produits = livraison.getProduits();
                        for (ProduitCmd produit : produits) {
                  %>
                        <p><%=produit.getQuantite()+" x "+produit.getProduit().getNom()%></p>
                  <%
                    }
                  %>
                    </td>
                  <td><%=client.getNom()%></td>
                  <td><%=livraison.getAdresse()%></td>
                  <td><%=livraison.getTelephone()%></td>
                  <td><%=prix%></td>
                <td><%=livraison.getEtat()%></td>
              </tr>
          <%
            }
          %>
        </tbody>
    </table>
</div>


<jsp:include page="/include/js.jsp"/>
</body>
</html>
