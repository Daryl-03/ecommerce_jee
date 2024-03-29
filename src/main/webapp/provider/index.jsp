<%@ page import="java.util.List" %>
<%@ page import="com.hermesstore.projetexamen2021.model.Categorie" %>
<%@ page import="com.hermesstore.projetexamen2021.model.datasource.CategorieDAO" %>
<%@ page import="com.hermesstore.projetexamen2021.model.Produit" %>
<%@ page import="com.hermesstore.projetexamen2021.model.datasource.ProduitDAO" %>
<%@ page import="com.hermesstore.projetexamen2021.model.datasource.UtilisateurDAO" %>
<%@ page import="com.hermesstore.projetexamen2021.model.Utilisateur" %>
<%@ page import="com.hermesstore.projetexamen2021.model.Fournisseur" %>
<%@ page import="com.hermesstore.projetexamen2021.exceptions.DAOException" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Naku
  Date: 20/03/2023
  Time: 18:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Utilisateur user = (Utilisateur) session.getAttribute("userC");
  if (user==null) {
    response.sendRedirect("/HermesStore/login.jsp");
    return;
  } else if (user.getProfil().equals("Client")) {
    response.sendRedirect("/HermesStore/client");
  }

  Fournisseur fournisseur = (Fournisseur) user;

  List<Categorie> categories = new CategorieDAO().readAll();
  ProduitDAO produitDAO = new ProduitDAO();
  List<Produit> produits = new ArrayList<>() ;
  int idCategorie = 0;
  if(request.getParameter("nomP")!=null){
    produits = produitDAO.readAllByFournisseurAndName(fournisseur.getId(), request.getParameter("nomP"));
  } else if(request.getParameter("categorie")!=null){
    if(request.getParameter("categorie").equals("all")){
      produits = produitDAO.readAllByFournisseur(fournisseur.getId());
    } else {
      idCategorie = Integer.parseInt(request.getParameter("categorie"));
      try {
        produits = produitDAO.readAllByCategorieAndFournisseur(idCategorie, fournisseur.getId());
      } catch (DAOException e) {
        e.printStackTrace();
      }
    }
  } else {
    produits = produitDAO.readAllByFournisseur(fournisseur.getId());
  }


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
    <li class="breadcrumb-item"><a href="#">Accueil</a></li>
    <li class="breadcrumb-item active" aria-current="page">Mes produits</li>
  </ul>
  <main class="row pl-3 mx-0" >


    <!-- show categories -->
    <div class="col-md-2">
      <div class="list-group mt-4">
        <a href="index.jsp?categorie=all" class="list-group-item list-group-item-action <%=idCategorie==0?"active":""%>">
          All Products</a>
        <%
          for (Categorie c : categories) {
        %>
        <a href="index.jsp?categorie=<%=c.getId()%>" class="list-group-item list-group-item-action <%=idCategorie==c.getId()?"active":""%> "><%=c.getNom()%></a>
        <%
          }
        %>
      </div>
    </div>

    <!-- show products -->
    <%
      if(produits.size()==0){
    %>
        <div class="col-md-10">
            <img src="../include/images/recherche.svg" style="width: 70vw; height: 70vh;">
            <h3 class="text-center">Aucun produit trouvé</h3>
        </div>
    <%
        }
    %>

    <div class="container mt-4 d-flex flex-wrap col-md-10">

      <%
        for (Produit p : produits) {
      %>
          <div class="col-md-4 mb-3">
            <div class="card product-card">
              <img src=<%="../store/"+p.getImage()%> class="card-img-top" alt="...">
              <div class="card-body">
                <h5 class="card-title"><%=p.getNom()%> </h5>
                <p class="card-text"><%=p.getPrixUnitaire()%> &euro;</p>
                <p class="card-text">En stock : <%=p.getQuantite()%></p>
                <p class="card-text">Catégorie : <%=p.getCategorie().getNom()%></p>
                <a href="addProduit.jsp?id=<%=p.getId()%>">
                  <button type="button" class="btn btn-primary">Modifier</button>
                </a>
                <button onclick="deleteProduit(<%=p.getId()%>)" type="button" class="btn btn-danger">Supprimer</button>
              </div>
            </div>
          </div>

      <%
        }
      %>
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
      function deleteProduit(id) {
                    if (confirm('Voulez-vous vraiment supprimer ce produit ?')) {
                        window.location.href = 'produit/delete?id='+id;
                    }
              }
    </script>
</body>
</html>
