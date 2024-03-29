package com.hermesstore.projetexamen2021.controller;

import com.hermesstore.projetexamen2021.exceptions.DAOException;
import com.hermesstore.projetexamen2021.model.*;
import com.hermesstore.projetexamen2021.model.datasource.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ClientCommandeServlet", urlPatterns = {"/client/ClientCommandeServlet", "/client/ClientCommandeServlet/save"})
public class ClientCommandeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("userC");
        if (user == null) {
            response.sendRedirect("/HermesStore/login.jsp");
            return;
        } else if (user.getProfil().equals("Fournisseur")) {
            response.sendRedirect("/HermesStore/provider");
        }
        
        String requestURI = request.getRequestURI();
        if(requestURI.endsWith("/save")) {
            saveCommande(request, response);
        } else {
            response.sendError(404, "Page non trouvée");
        }
    }
    
    private void saveCommande(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String adresse = request.getParameter("adresse");
        String telephone = request.getParameter("tel");
        
        HttpSession session = request.getSession();
        Client user = (Client) session.getAttribute("userC");
        List<Livraison> livraisons = (List<Livraison>) session.getAttribute("livraisons");
        try {
            Panier panier = new PanierDAO().read(user.getId());
            Commande commande = new Commande();
            commande.setId_client(user.getId());
            commande.setPrix(panier.getPrix()+livraisons.size()*10);
            int idCmd = new CommandeDAO().create(commande);
            for(Livraison livraison : livraisons) {
                livraison.setAdresse(adresse);
                livraison.setTelephone(telephone);
                livraison.setId_cmd(idCmd);
                int idLiv = new LivraisonDAO().create(livraison);
                for (ProduitCmd produitCmd : livraison.getProduits()) {
                    produitCmd.setId_livraison(idLiv);
                    new ProduitCmdDAO().create(produitCmd);
                    produitCmd.getProduit().setQuantite(produitCmd.getProduit().getQuantite() - produitCmd.getQuantite());
                    new ProduitDAO().update(produitCmd.getProduit());
                }
            }
            new PanierDAO().emptyPanier(panier.getId());
            session.setAttribute("panier", new PanierDAO().read(panier.getId()));
            session.setAttribute("livraisons", null);
            session.setAttribute("success", "Commande effectuée avec succès");
            response.sendRedirect("/HermesStore/client");
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
        
        
    }
}
