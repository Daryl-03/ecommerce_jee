package com.hermesstore.projetexamen2021.controller;

import com.hermesstore.projetexamen2021.exceptions.DAOException;
import com.hermesstore.projetexamen2021.model.Client;
import com.hermesstore.projetexamen2021.model.Fournisseur;
import com.hermesstore.projetexamen2021.model.Utilisateur;
import com.hermesstore.projetexamen2021.model.datasource.ClientDAO;
import com.hermesstore.projetexamen2021.model.datasource.FournisseurDAO;
import com.hermesstore.projetexamen2021.util.PasswordUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ProfilServlet", urlPatterns = {"/client/Profil/client", "/provider/Profil/provider","/provider/Password/provider", "/client/Password/client" })
public class ProfilServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("userC");
        System.out.println(requestURI);
        if(user == null) {
            response.sendRedirect("/HermesStore/login.jsp");
            return;
        } else if(requestURI.endsWith("/client") && user.getProfil().equals("Client")) {
            if(requestURI.contains("Password"))
                updatePasswordClient(request, response);
            else
                updateClient(request, response);
        } else if (requestURI.endsWith("/provider") && user.getProfil().equals("Fournisseur")) {
            if(requestURI.contains("Password"))
                updatePasswordProvider(request, response);
            else
                updateProvider(request, response);
        } else {
            response.sendError(500, "Erreur interne");
        }
    }
    
    private void updatePasswordProvider(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Fournisseur fournisseur = (Fournisseur) session.getAttribute("userC");
        String oldPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        if(oldPassword.equals(fournisseur.getPassword())) {
            if(newPassword.equals(confirmPassword)) {
                newPassword = PasswordUtil.encrypt(newPassword);
                fournisseur.setPassword(newPassword);
                FournisseurDAO fournisseurDAO = new FournisseurDAO();
                try {
                    fournisseurDAO.update(fournisseur);
                    session.setAttribute("userC", fournisseur);
                    session.setAttribute("success", "Votre mot de passe a été mis à jour avec succès");
                    response.sendRedirect("/HermesStore/provider/profile.jsp");
                } catch (DAOException | IOException e) {
                    e.printStackTrace();
                }
            } else {
                session.setAttribute("error", "Les mots de passe ne correspondent pas");
                try {
                    response.sendRedirect("/HermesStore/provider/profile.jsp");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            session.setAttribute("error", "Le mot de passe actuel est incorrect");
            try {
                response.sendRedirect("/HermesStore/provider/profile.jsp");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void updatePasswordClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("userC");
        String oldPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        if(oldPassword.equals(client.getPassword())) {
            if(newPassword.equals(confirmPassword)) {
                newPassword = PasswordUtil.encrypt(newPassword);
                client.setPassword(newPassword);
                ClientDAO clientDAO = new ClientDAO();
                try {
                    clientDAO.update(client);
                    session.setAttribute("userC", client);
                    session.setAttribute("success", "Votre mot de passe a été mis à jour avec succès");
                    response.sendRedirect("/HermesStore/client/profile.jsp");
                } catch (DAOException | IOException e) {
                    e.printStackTrace();
                    response.sendError(500, "Erreur interne");
                }
            } else {
                session.setAttribute("error", "Les mots de passe ne correspondent pas");
                try {
                    response.sendRedirect("/HermesStore/client/profile.jsp");
                } catch (IOException e) {
                    e.printStackTrace();
                    response.sendError(500, "Erreur interne");
                }
            }
        } else {
            session.setAttribute("error", "Le mot de passe actuel est incorrect");
            try {
                response.sendRedirect("/HermesStore/client/profile.jsp");
            } catch (IOException e) {
                e.printStackTrace();
                response.sendError(500, "Erreur interne");
            }
        }
    }
    
    private void updateProvider(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Fournisseur fournisseur = (Fournisseur) session.getAttribute("userC");
        fournisseur.setNom(request.getParameter("nom"));
        fournisseur.setAdresse(request.getParameter("adresse"));
        fournisseur.setNationalite(request.getParameter("nationalite"));
        fournisseur.setTelephone(request.getParameter("tel"));
        fournisseur.setLogin(request.getParameter("email"));
        
        FournisseurDAO fournisseurDAO = new FournisseurDAO();
        try {
            fournisseurDAO.update(fournisseur);
            session.setAttribute("userC", fournisseur);
            session.setAttribute("success", "Votre profil a été mis à jour avec succès");
            response.sendRedirect("/HermesStore/provider/profile.jsp");
        } catch (DAOException e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }
    
    private void updateClient(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Client client = (Client) session.getAttribute("userC");
        client.setNom(request.getParameter("nom"));
        client.setAdresse(request.getParameter("adresse"));
        client.setLogin(request.getParameter("email"));
        client.setTelephone(request.getParameter("tel"));
        client.setType(request.getParameter("type"));
        if(request.getParameter("type").equals("entreprise"))
            client.setSpecialite(request.getParameter("specialite"));
        
        ClientDAO clientDAO = new ClientDAO();
        try {
            clientDAO.update(client);
            session.setAttribute("userC", client);
            session.setAttribute("success", "Votre profil a été mis à jour avec succès");
            response.sendRedirect("/HermesStore/client/profile.jsp");
        } catch (DAOException e) {
            e.printStackTrace();
            response.sendError(500);
        }
    }
}
