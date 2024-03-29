<%@ page import="java.util.ArrayList" %>
<%@ page import="com.hermesstore.projetexamen2021.model.datasource.ProduitDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.hermesstore.projetexamen2021.exceptions.DAOException" %>
<%@ page import="com.hermesstore.projetexamen2021.model.datasource.CategorieDAO" %>
<%@ page import="com.hermesstore.projetexamen2021.model.*" %>
<%@ page import="com.hermesstore.projetexamen2021.model.datasource.PanierDAO" %><%--
  Created by IntelliJ IDEA.
  User: Naku
  Date: 23/03/2023
  Time: 04:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Utilisateur user = (Utilisateur) session.getAttribute("userC");
  if (user==null) {
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
</head>
<body>
    <jsp:include page="header.jsp"/>
    <ul class="breadcrumb row mx-0 px-3">
    <li class="breadcrumb-item"><a href="index.jsp">Accueil</a></li>
    <li class="breadcrumb-item active" aria-current="page">Mon panier</li>
    </ul>
    <div class="container row mx-auto">
        <table class="table mx-auto table-hover">
            <thead class="thead-light">
                <tr>
                    <th scope="col" >Produit</th>
                    <th scope="col">Prix unitaire</th>
                    <th scope="col">Quantité</th>
                    <th scope="col">Total</th>
                    <th scope="col"></th>
                </tr>
            </thead>
            <tbody>
                <%
                    for (ProduitPanier produitPanier : panier.getProduits()) {
                    int idProduitPanier = produitPanier.getId();
                %>
                <tr>
                    <td><%=produitPanier.getProduit().getNom()%></td>
                    <td><%=produitPanier.getProduit().getPrixUnitaire()%> &euro;</td>
                    <td>
                        <div class="input-group">
                            <button onclick="reductionQuantite(<%=idProduitPanier%>)" class="btn btn-outline-secondary" type="button" id="button-addon1">-</button>
                            <input readonly min="1" max="<%=produitPanier.getProduit().getQuantite()%>" type="number" class="form-control" placeholder="Quantité" aria-label="Quantité" aria-describedby="button-addon1" value="<%=produitPanier.getQuantite()%>">
                            <button onclick="augmentationQuantite(<%=idProduitPanier%>)" class="btn btn-outline-secondary" type="button" id="button-addon2">+</button>
                        </div>
                    </td>
                    <td><%=produitPanier.getProduit().getPrixUnitaire()* produitPanier.getQuantite()%> &euro;</td>
                    <td><button onclick="deleteItem(<%=idProduitPanier%>)" class="btn btn-danger"><i class="bx bx-trash"></i></button></td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
    <div class="text-right mr-5 pl-2 row justify-content-end">
        <h4>Total : <%=panier.getPrix()%> &euro;</h4>
    </div>
    <div class="text-center row justify-content-center">
        <button onclick="commander()" class="btn btn-primary">Commander</button>
    </div>


    <jsp:include page="/include/js.jsp"/>
    <jsp:include page="footer.jsp"/>
    <script>
        function deleteItem(id) {
            if(!confirm("Voulez-vous vraiment supprimer cet élément ?")) {
                return;
            }
            $.ajax({
                url: "/HermesStore/PanierServlet",
                type: "POST",
                data: {
                    action: "delete",
                    id: id
                },
                success: function (data) {
                    setTimeout(function () {
                        location.reload();
                    }, 500);
                    toastr.success("Produit supprimé");
                }
            });
        }

        function augmentationQuantite(id) {
            $.ajax({
                url: "/HermesStore/PanierServlet",
                type: "POST",
                data: {
                    action: "augmentation",
                    id: id
                },
                success: function (data) {
                    setTimeout(function () {
                        location.reload();
                    }, 500);
                    toastr.success("Quantité augmentée");
                }
            });
        }

        function reductionQuantite(id) {
            $.ajax({
                url: "/HermesStore/PanierServlet",
                type: "POST",
                data: {
                    action: "reduction",
                    id: id
                },
                success: function (data) {
                    setTimeout(function () {
                        location.reload();
                    }, 500);
                    toastr.success("Quantité réduite");
                }
            });
        }

        function commander() {
            window.location.href = "/HermesStore/PanierServlet/commander";
            // toastr.success("Commande effectuée");
            // $.ajax({
            //     url: "/HermesStore/PanierServlet",
            //     type: "POST",
            //     data: {
            //         action: "commander"
            //     }
            // });
        }
    </script>
</body>
</html>
