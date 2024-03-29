<%@ page import="java.util.ArrayList" %>
<%@ page import="com.hermesstore.projetexamen2021.model.datasource.CategorieDAO" %>
<%@ page import="com.hermesstore.projetexamen2021.model.datasource.ProduitDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.hermesstore.projetexamen2021.model.*" %>
<%@ page import="com.hermesstore.projetexamen2021.exceptions.DAOException" %>
<%@ page import="com.hermesstore.projetexamen2021.model.datasource.FournisseurDAO" %><%--
  Created by IntelliJ IDEA.
  User: Naku
  Date: 19/03/2023
  Time: 02:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Utilisateur user = (Utilisateur) session.getAttribute("userC");
    if (user == null) {
        response.sendRedirect("/HermesStore/login.jsp");
        return;
    } else if (user.getProfil().equals("Fournisseur")) {
        response.sendRedirect("/HermesStore/provider");
    }

    Client fournisseur = (Client) user;

    List<Categorie> categories = new CategorieDAO().readAll();
    ProduitDAO produitDAO = new ProduitDAO();
    List<Produit> produits = new ArrayList<>();
    int idCategorie = 0;
    if (request.getParameter("nomP") != null) {
        produits = produitDAO.readAllByName(request.getParameter("nomP"));
    } else if (request.getParameter("categorie") != null) {
        if (request.getParameter("categorie").equals("all")) {
            produits = produitDAO.readAll();
        } else {
            idCategorie = Integer.parseInt(request.getParameter("categorie"));
            try {
                produits = produitDAO.readAllByCategorie(idCategorie);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        }
    } else {
        produits = produitDAO.readAll();
    }


%>
<html>
<head>
    <title>Accueil Client</title>
    <jsp:include page="/include/css.jsp"/>
    <link rel="stylesheet" href="../include/css/provider/providerIndex.css">
</head>
<body>
<jsp:include page="header.jsp"/>

<ul class="breadcrumb row mx-0 px-3">
    <li class="breadcrumb-item"><a href="#">Accueil</a></li>
    <li class="breadcrumb-item active" aria-current="page">Catalogue</li>
</ul>
<main class="row pl-3 mx-0">


    <!-- show categories -->
    <div class="col-md-2">
        <div class="list-group mt-4">
            <a href="index.jsp?categorie=all"
               class="list-group-item list-group-item-action <%=idCategorie==0?"active":""%>">
                All Products</a>
            <%
                for (Categorie c : categories) {
            %>
            <a href="index.jsp?categorie=<%=c.getId()%>"
               class="list-group-item list-group-item-action <%=idCategorie==c.getId()?"active":""%> "><%=c.getNom()%>
            </a>
            <%
                }
            %>
        </div>
    </div>

    <!-- show products -->
    <%
        if (produits.size() == 0) {
    %>
    <div class="col-md-10">
        <img src="../include/images/recherche.svg" style="width: 70vw; height: 70vh;">
        <h3 class="text-center">Aucun produit</h3>
    </div>
    <%
        }
    %>

    <div class="container mt-4 d-flex flex-wrap col-md-10">

        <%
            for (Produit p : produits) {
                Fournisseur f = new FournisseurDAO().read(p.getIdFournisseur());
        %>
        <div class="col-md-4 mb-3">
            <div class="card shadow product-card">
                <img src=<%="../store/"+p.getImage()%> class="card-img-top" alt="...">
                <div class="card-body">
                    <h5 class="card-title"><%=p.getNom()%>
                    </h5>
                    <p class="card-text"><%=p.getPrixUnitaire()%> &euro;</p>
                    <p class="card-text">Fournisseur : <%=f.getNom()%>
                    </p>
                    <p class="card-text">En stock : <%=p.getQuantite()%>
                    </p>
                    <p class="card-text">Catégorie : <%=p.getCategorie().getNom()%>
                    </p>
                    <a href="/HermesStore/PanierServlet?id=<%=p.getId()%>" class="text-decoration-none">
                        <button type="button" class="btn btn-warning btn-block">Ajouter au panier<i
                                class="bx text-primary bx-cart-add"></i></button>
                    </a>
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
</body>
</html>
