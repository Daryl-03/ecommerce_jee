<%@ page import="com.hermesstore.projetexamen2021.model.datasource.PanierDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.hermesstore.projetexamen2021.model.datasource.CommandeDAO" %>
<%@ page import="com.hermesstore.projetexamen2021.model.*" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.util.Locale" %><%--
  Created by IntelliJ IDEA.
  User: Naku
  Date: 23/03/2023
  Time: 22:34
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
    List<Commande> commandes = new CommandeDAO().readAllByClient(client.getId());

%>
<html>
<head>
    <title>Commandes</title>
    <jsp:include page="/include/css.jsp"/>
</head>
<body>
<jsp:include page="header.jsp"/>

<ul class="breadcrumb row mx-0 px-3">
    <li class="breadcrumb-item"><a href="#">Accueil</a></li>
    <li class="breadcrumb-item active" aria-current="page">Mes commandes</li>
</ul>

<div class="container row mx-auto">
    <div class="col-12 mx-0 w-100 " id="cmdTbl">

        <table class="table table-hover w-100">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Date de commande</th>
                <th scope="col">Montant total</th>
                <th scope="col">Statut de la commande</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <%
                int i = 0;
                for (Commande commande : commandes) {
            %>
            <tr>
                <th scope="row"><%=++i%>
                </th>
                <td><%=commande.getDate()%>
                </td>
                <td><%=commande.getPrix()%> &euro;</td>
                <td><%=commande.getEtat()%>
                </td>
                <td>
                    <button class="btn btn-link" type="button" data-toggle="collapse"
                            data-target="#commande<%=commande.getId()%>" aria-expanded="false"
                            aria-controls="#commande<%=commande.getId()%>"><i class="bx bx-detail"></i></button>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>

    <div class="col-5 mx-0">
        <%
            for (Commande commande : commandes) {
        %>

        <div class="collapse container" id="commande<%=commande.getId()%>">
            <h4>Détails de livraison</h4>
            <%
                List<Livraison> livraisons = commande.getLivraisons();
                i = 0;
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
                        entre <%=commande.getDate().format(DateTimeFormatter.ofPattern("EEEE dd MMMM", Locale.FRENCH))%>
                        et <%=commande.getDate().plusDays(5).format(DateTimeFormatter.ofPattern("EEEE dd MMMM", Locale.FRENCH))%>
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
                            <td><%=commande.getPrix() - livraisons.size() * 10%> &euro;</td>
                        </tr>
                        <tr>
                            <th scope="row">Montant de livraison</th>
                            <td><%=livraisons.size() * 10%> &euro;</td>
                        </tr>
                        <tr>
                            <th scope="row">Total</th>
                            <td><%=commande.getPrix()%>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
        <%
            }
        %>
    </div>


</div>


<jsp:include page="/include/js.jsp"/>
<script>
    <%
        for (Commande commande : commandes) {
    %>
        $("#commande<%=commande.getId()%>").on("show.bs.collapse", function () {
            if($("#cmdTbl").hasClass("col-12"))
                $("#cmdTbl").removeClass("col-12");
            if (!$("#cmdTbl").hasClass("col-7"))
                $("#cmdTbl").addClass("col-7");
        });
        $("#commande<%=commande.getId()%>").on("hide.bs.collapse", function () {
            // recherche le nombre de détails ouverts
            let nb = 0;
            $(".collapse").each(function () {
                if ($(this).hasClass("show")) {
                    nb++;
                    console.log(nb);
                }
            });
            if (nb == 1) {
                $("#cmdTbl").removeClass("col-7");
                $("#cmdTbl").addClass("col-12");
            }
        });
    <%
        }
    %>
</script>
</body>
</html>
