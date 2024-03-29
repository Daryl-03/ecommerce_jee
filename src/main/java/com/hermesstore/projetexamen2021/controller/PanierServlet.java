package com.hermesstore.projetexamen2021.controller;

import com.hermesstore.projetexamen2021.exceptions.DAOException;
import com.hermesstore.projetexamen2021.model.*;
import com.hermesstore.projetexamen2021.model.datasource.PanierDAO;
import com.hermesstore.projetexamen2021.model.datasource.ProduitDAO;
import com.hermesstore.projetexamen2021.model.datasource.ProduitPanierDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "PanierServlet", urlPatterns = {"/PanierServlet", "/PanierServlet/commander"})
public class PanierServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("userC");
        if (user == null) {
            response.sendRedirect("/HermesStore/login.jsp");
            return;
        } else if (user.getProfil().equals("Fournisseur")) {
            response.sendRedirect("/HermesStore/provider");
        }
        Client client = (Client) user;
        
        if(request.getParameter("id")!=null){
            try {
                Panier panier = new PanierDAO().readByClient(user.getId());
                int id = Integer.parseInt(request.getParameter("id"));
                ProduitPanierDAO produitPanierDAO = new ProduitPanierDAO();
                ProduitPanier produitPanier = panier.existProduit(id);
                if(produitPanier!=null) {
                    if(produitPanier.getProduit().getQuantite()+1>produitPanier.getQuantite()){
                        produitPanier.setQuantite(produitPanier.getQuantite()+1);
                        produitPanierDAO.update(produitPanier);
                        session.setAttribute("success", "Produit ajouté au panier");
                        
                    } else {
                        session.setAttribute("error", "Quantité maximale atteinte");
                    }
                } else {
                    produitPanier = new ProduitPanier();
                    produitPanier.setId_panier(panier.getId());
                    produitPanier.setProduit(new ProduitDAO().read(id));
                    produitPanier.setQuantite(1);
                    produitPanierDAO.create(produitPanier);
                    session.setAttribute("success", "Produit ajouté au panier");
                }
                getServletContext().getRequestDispatcher("/client").forward(request, response);
            } catch (DAOException e) {
                e.printStackTrace();
                response.sendError(500, "Erreur interne");
                
            }
        } else {
            String uri = request.getRequestURI();
            if(uri.endsWith("/commander")){
                PanierDAO panierDAO = new PanierDAO();
                Panier panier = null;
                try {
                    panier = panierDAO.readByClient(user.getId());
                    panier.refresh();
                    panierDAO.update(panier);
                    List<Livraison> livraisons = createLivraisons(panier);
                    session.setAttribute("livraisons", livraisons);
                    response.sendRedirect("/HermesStore/client/checkout.jsp");
                    return;
                } catch (DAOException e) {
                    e.printStackTrace();
                    response.sendError(500, "Erreur interne");
                    return;
                }
            }
            response.sendRedirect("/HermesStore/client");
        }
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
        try {
            System.out.println("PanierServlet");
            if(request.getParameter("id")!=null){
                ProduitPanierDAO produitPanierDAO = new ProduitPanierDAO();
                ProduitPanier produitPanier = produitPanierDAO.read(Integer.parseInt(request.getParameter("id")));
                String action = request.getParameter("action");
                switch (action){
                    case "augmentation" :
                        produitPanier.setQuantite(produitPanier.getQuantite() + 1);
                        produitPanierDAO.update(produitPanier);
                        break;
                    case "reduction" :
                        if(produitPanier.getQuantite()==1){
                            produitPanierDAO.delete(produitPanier.getId());
                        } else {
                            produitPanier.setQuantite(produitPanier.getQuantite() - 1);
                            produitPanierDAO.update(produitPanier);
                        }
                        break;
                    case "delete" :
                        produitPanierDAO.delete(produitPanier.getId());
                        break;
                    default:
                        response.sendError(404, "Page non trouvée");
        
                }
                
            } else if(request.getParameter("action")!=null){
                String action = request.getParameter("action");
                if(action.equals("commander")){
                    System.out.println("Commander");
                    PanierDAO panierDAO = new PanierDAO();
                    Panier panier = panierDAO.readByClient(user.getId());
                    panier.refresh();
                    panierDAO.update(panier);
                    List<Livraison> livraisons = createLivraisons(panier);
                    session.setAttribute("livraisons", livraisons);
                    response.sendRedirect("/HermesStore/client/checkout.jsp");
                }
            }
        } catch (DAOException e) {
            e.printStackTrace();
            response.sendError(500, "Erreur interne");
        }
    }
    
    /**
     * Crée une liste de livraisons à partir d'un panier. Une livraison est créée pour chaque fournisseur et contient tous les produits de ce fournisseur du panier.
     * @param panier
     * @return liste de livraisons
     */
    private List<Livraison> createLivraisons(Panier panier){
        List<Livraison> livraisons = new ArrayList<>();
        for (ProduitPanier produitPanier : panier.getProduits()) {
            boolean found = false;
            for (Livraison livraison : livraisons) {
                if (livraison.getId_fournisseur() == produitPanier.getProduit().getIdFournisseur()) {
                    ProduitCmd produitCmd = new ProduitCmd(produitPanier);
                    livraison.getProduits().add(produitCmd);
                    found = true;
                    break;
                }
            }
            if (!found) {
                Livraison livraison = new Livraison();
                livraison.setId_fournisseur(produitPanier.getProduit().getIdFournisseur());
                livraison.getProduits().add(new ProduitCmd(produitPanier));
                livraisons.add(livraison);
            }
        }
        return livraisons;
    }
}
