<%@ page import="java.util.ArrayList" %>
<%@ page import="com.hermesstore.projetexamen2021.model.datasource.ProduitDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.hermesstore.projetexamen2021.exceptions.DAOException" %>
<%@ page import="com.hermesstore.projetexamen2021.model.datasource.CategorieDAO" %>
<%@ page import="com.hermesstore.projetexamen2021.model.*" %>
<%@ page import="com.hermesstore.projetexamen2021.model.datasource.PanierDAO" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Locale" %><%--
  Created by IntelliJ IDEA.
  User: Naku
  Date: 23/03/2023
  Time: 04:49
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

    Client client = (Client) user;
    Panier panier = new PanierDAO().readByClient(client.getId());

%>
<html>
<head>
    <title>Panier</title>
    <jsp:include page="/include/css.jsp"/>
    <link rel="stylesheet" href="../include/css/client/checkout.css">
</head>
<body>
<jsp:include page="header.jsp"/>
<ul class="breadcrumb row mx-0 px-3">
    <li class="breadcrumb-item"><a href="index.jsp">Accueil</a></li>
    <li class="breadcrumb-item active" aria-current="page">Commande</li>
</ul>

<div class="row mx-0">
    <div class="col-8 container">
        <h1>Détails de livraison</h1>
        <%
            List<Livraison> livraisons = (List<Livraison>) session.getAttribute("livraisons");
            int i = 0;
            for (Livraison livraison : livraisons) {
                i++;
        %>
        <div class="card mb-3">
            <div class="card-header">Livraison <%=i%>/<%=livraisons.size()%>
            </div>
            <div class="card-body">
                <%
                    for (ProduitCmd produitCmd : livraison.getProduits()) {
                %>
                <h6 class="card-title"><%=produitCmd.getQuantite()%>x <%=produitCmd.getProduit().getNom()%>
                </h6>
                <%
                    }
                %>
                <p class="card-text">Livré
                    entre <%=LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE dd MMMM", Locale.FRENCH))%>
                    et <%=LocalDate.now().plusDays(5).format(DateTimeFormatter.ofPattern("EEEE dd MMMM", Locale.FRENCH))%>
                </p>
            </div>
        </div>
        <%
            }
        %>
        <div class="row justify-content-end">
            <div class="col-md-6">
                <table class="table">
                    <tbody>
                    <tr>
                        <th scope="row">Sous-total</th>
                        <td><%=panier.getPrix()%> &euro;</td>
                    </tr>
                    <tr>
                        <th scope="row">Montant de livraison</th>
                        <td><%=livraisons.size()*10%> &euro;</td>
                    </tr>
                    <tr>
                        <th scope="row">Total</th>
                        <td><%=livraisons.size()*10 + panier.getPrix()%> &euro;</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </div>

    <div class="col-4">
        <h2>Adresse de livraison</h2>
        <form method="post" action="ClientCommandeServlet/save" >
            <div class="form-group">
                <label for="nom">Nom</label>
                <input type="text" readonly class="form-control" name="nom" id="nom" value="<%=client.getNom()%>">
            </div>
            <div class="form-group">
                <label for="adresse">Adresse</label>
                <input type="text" class="form-control" name="adresse" id="adresse" value="<%=client.getAdresse()%>">
            </div>
            <div class="form-group">
                <label for="tel">Téléphone</label>
                <input type="tel" class="form-control" name="tel" id="tel" value="<%=client.getTelephone()%>">
            </div>
            <button type="submit" class="btn btn-primary">Valider</button>
        </form>
    </div>
</div>


<jsp:include page="footer.jsp"/>
<jsp:include page="/include/js.jsp"/>

</body>
</html>
