package com.hermesstore.projetexamen2021.controller;

import com.hermesstore.projetexamen2021.model.Categorie;
import com.hermesstore.projetexamen2021.model.Fournisseur;
import com.hermesstore.projetexamen2021.model.Produit;
import com.hermesstore.projetexamen2021.model.Utilisateur;
import com.hermesstore.projetexamen2021.model.datasource.CategorieDAO;
import com.hermesstore.projetexamen2021.model.datasource.ProduitDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.*;
import java.nio.file.Files;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "ProduitFournisseurServlet", urlPatterns = {"/provider/produit/*"})
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10, // 10MB
    maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class ProduitFournisseurServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("userC");
        if (user == null) {
            response.sendRedirect("/HermesStore/login.jsp");
            return;
        } else if (user.getProfil().equals("Client")) {
            response.sendRedirect("/HermesStore/client");
        }
        doPost(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("userC");
        if (user == null) {
            response.sendRedirect("/HermesStore/login.jsp");
            return;
        } else if (user.getProfil().equals("Client")) {
            response.sendRedirect("/HermesStore/client");
        }
        
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        
        if(requestURI.endsWith("/add")) {
            addProduit(request, response);
        } else if(requestURI.endsWith("/update")) {
            updateProduit(request, response);
        } else if(requestURI.contains("/delete")) {
            deleteProduit(request, response);
        } else {
            response.sendError(404, "Page non trouvée");
        }
    }
    
    private void deleteProduit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            Produit produit = new ProduitDAO().read(id);
            File ancienneImage = new File(getServletContext().getRealPath("/store")+File.separator+produit.getImage());
            if(ancienneImage.delete()) {
                System.out.println("Ancienne image supprimée");
            } else {
                System.out.println("Ancienne image non supprimée");
            }
            new ProduitDAO().delete(id);
            request.getSession().setAttribute("success", "Produit supprimé avec succès");
            response.sendRedirect("/HermesStore/provider");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Erreur lors de la suppression du produit");
        }
    }
    
    private void updateProduit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Produit produit = null;
        try {
            produit = new ProduitDAO().read(Integer.parseInt(request.getParameter("id")));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Erreur lors de la modification du produit");
        }
        produit.setId(Integer.parseInt(request.getParameter("id")));
        produit.setNom(request.getParameter("nom"));
        produit.setPrixUnitaire(Double.parseDouble(request.getParameter("prixUn")));
        produit.setQuantite(Double.parseDouble(request.getParameter("qte")));
        if(request.getParameter("dateFab") != null && !request.getParameter("dateFab").isEmpty()) {
            produit.setDateFabrication(LocalDate.parse(request.getParameter("dateFab")));
        }
        if(request.getParameter("dateExp") != null && !request.getParameter("dateExp").isEmpty()) {
            produit.setDateExpiration(LocalDate.parse(request.getParameter("dateExp")));
        }
        Fournisseur fournisseur = (Fournisseur) request.getSession().getAttribute("userC");
        produit.setIdFournisseur(fournisseur.getId());
        String dateFab = request.getParameter("dateFab");
        if(dateFab != null && !dateFab.isEmpty()) {
            produit.setDateFabrication(LocalDate.parse(dateFab));
        }
        String dateExp = request.getParameter("dateExp");
        if(dateExp != null && !dateExp.isEmpty()) {
            produit.setDateExpiration(LocalDate.parse(dateExp));
        }
        Part image = request.getPart("image");
        if(!image.getSubmittedFileName().isEmpty()) {
            File ancienneImage = new File(getServletContext().getRealPath("/store")+File.separator+produit.getImage());
            if(ancienneImage.delete()) {
                System.out.println("Ancienne image supprimée");
            } else {
                System.out.println("Ancienne image non supprimée");
            }
            String nomImage = fournisseur.getCode()+image.getSubmittedFileName();
            produit.setImage(nomImage);
            
            String path = getServletContext().getRealPath("/store")+File.separator+nomImage;
            FileOutputStream fos = new FileOutputStream(path);
            InputStream is = image.getInputStream();
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            fos.write(buffer);
            fos.close();
        }
    
        try {
            produit.setCategorie(new CategorieDAO().read(Integer.parseInt(request.getParameter("categorie"))));
            new ProduitDAO().update(produit);
            request.getSession().setAttribute("success", "Produit modifié avec succès");
            response.sendRedirect("/HermesStore/provider/");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Erreur lors de la modification du produit");
        }

    }
    
    private void addProduit(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Produit produit = new Produit();
        produit.setNom(request.getParameter("nom"));
        produit.setPrixUnitaire(Double.parseDouble(request.getParameter("prixUn")));
        produit.setQuantite(Double.parseDouble(request.getParameter("qte")));
        Fournisseur fournisseur = (Fournisseur) request.getSession().getAttribute("userC");
        produit.setIdFournisseur(fournisseur.getId());
        String dateFab = request.getParameter("dateFab");
        if(dateFab != null && !dateFab.isEmpty()) {
            produit.setDateFabrication(LocalDate.parse(dateFab));
        }
        String dateExp = request.getParameter("dateExp");
        if(dateExp != null && !dateExp.isEmpty()) {
            produit.setDateExpiration(LocalDate.parse(dateExp));
        }
        Part image = request.getPart("image");
        String nomImage = fournisseur.getCode()+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm"))+"-"+image.getSubmittedFileName();
        produit.setImage(nomImage);
        
        try {
            String path = getServletContext().getRealPath("/store")+File.separator+nomImage;
            FileOutputStream fos = new FileOutputStream(path);
            InputStream is = image.getInputStream();
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            fos.write(buffer);
            fos.close();
            //
            produit.setCategorie(new CategorieDAO().read(Integer.parseInt(request.getParameter("categorie"))));
            new ProduitDAO().create(produit);
            request.getSession().setAttribute("success", "Produit ajouté avec succès");
            response.sendRedirect("/HermesStore/provider/");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500, "Erreur lors de la création du produit");
        }
    
    }
}
