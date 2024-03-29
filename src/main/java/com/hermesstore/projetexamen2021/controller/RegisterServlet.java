package com.hermesstore.projetexamen2021.controller;

import com.hermesstore.projetexamen2021.exceptions.DAOException;
import com.hermesstore.projetexamen2021.model.Client;
import com.hermesstore.projetexamen2021.model.Fournisseur;
import com.hermesstore.projetexamen2021.model.Panier;
import com.hermesstore.projetexamen2021.model.Utilisateur;
import com.hermesstore.projetexamen2021.model.datasource.ClientDAO;
import com.hermesstore.projetexamen2021.model.datasource.FournisseurDAO;
import com.hermesstore.projetexamen2021.model.datasource.PanierDAO;
import com.hermesstore.projetexamen2021.util.PasswordUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/provider/Register/provider", "/client/Register/client"})
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("userC");
        if(user!= null) {
            response.sendRedirect("/HermesStore/login.jsp");
            return;
        }
        String requestURI = request.getRequestURI();
        if(requestURI.endsWith("/client"))
            registerClient(request, response);
        else if (requestURI.endsWith("/provider")) {
            registerProvider(request, response);
        } else {
            response.sendError(404);
        }
    }
    
    private void registerProvider(HttpServletRequest request, HttpServletResponse response) throws IOException {
        FournisseurDAO fournisseurDAO = new FournisseurDAO();
        Fournisseur fournisseur = new Fournisseur();
        
        fournisseur.setLogin(request.getParameter("email"));
        fournisseur.setPassword(PasswordUtil.encrypt(request.getParameter("pwd")));
        fournisseur.setNom(request.getParameter("nom"));
        fournisseur.setAdresse(request.getParameter("adresse"));
        fournisseur.setNationalite(request.getParameter("nationalite"));
        fournisseur.setTelephone(request.getParameter("tel"));
        fournisseur.setProfil("Fournisseur");
    
        try {
            int id = fournisseurDAO.create(fournisseur);
            fournisseur.setId(id);
            HttpSession session = request.getSession();
            session.setAttribute("userC", fournisseur);
            getServletContext().getRequestDispatcher("/provider").forward(request, response);
        } catch (DAOException e) {
            e.printStackTrace();
            response.sendError(500, "Erreur lors de l'enregistrement du fournisseur");
        } catch (ServletException e) {
            e.printStackTrace();
            response.sendError(500, "Erreur lors de la redirection vers la page d'accueil");
        }
    }
    
    private void registerClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ClientDAO clientDAO = new ClientDAO();
        Client client = new Client();
        
        client.setLogin(request.getParameter("email"));
        client.setPassword(PasswordUtil.encrypt(request.getParameter("pwd")));
        client.setNom(request.getParameter("nom"));
        client.setAdresse(request.getParameter("adresse"));
        client.setType(request.getParameter("type"));
        client.setTelephone(request.getParameter("tel"));
        String specialite = request.getParameter("specialite");
        if(specialite != null)
            client.setSpecialite(specialite);
        client.setProfil("Client");
    
        try {
            int id = clientDAO.create(client);
            client.setId(id);
            Panier panier = new Panier();
            panier.setId_client(id);
            new PanierDAO().create(panier);
            HttpSession session = request.getSession();
            session.setAttribute("userC", client);
            getServletContext().getRequestDispatcher("/client").forward(request, response);
        } catch (DAOException e) {
            e.printStackTrace();
            response.sendError(500, "Erreur lors de l'enregistrement du client");
        } catch (ServletException e) {
            e.printStackTrace();
            response.sendError(500, "Erreur lors de la redirection vers la page d'accueil");
        }
    
    }
}
