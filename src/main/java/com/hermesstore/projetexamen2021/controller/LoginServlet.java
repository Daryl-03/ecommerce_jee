package com.hermesstore.projetexamen2021.controller;

import com.hermesstore.projetexamen2021.exceptions.DAOException;
import com.hermesstore.projetexamen2021.model.Client;
import com.hermesstore.projetexamen2021.model.Panier;
import com.hermesstore.projetexamen2021.model.Utilisateur;
import com.hermesstore.projetexamen2021.model.datasource.PanierDAO;
import com.hermesstore.projetexamen2021.model.datasource.UtilisateurDAO;
import com.hermesstore.projetexamen2021.util.PasswordUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("userC");
        if (user != null) {
            if(user.getProfil().equals("Fournisseur"))
              response.sendRedirect("/HermesStore/provider");
            return;
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("userC");
        if (user != null) {
            if(user.getProfil().equals("Fournisseur"))
              response.sendRedirect("/HermesStore/provider");
            return;
        }
        
        
        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        password = PasswordUtil.encrypt(password);
    
        try {
            Utilisateur utilisateur = utilisateurDAO.readByLoginAndPassword(login, password);
            System.out.println(login+" "+ password);
            if (utilisateur != null) {
                session.setAttribute("userC", utilisateur);
                if(utilisateur.getProfil().equals("Fournisseur"))
                    response.sendRedirect("/HermesStore/provider/");
                else if(utilisateur.getProfil().equals("Client"))
                    response.sendRedirect("/HermesStore/client/");
                
            } else {
                request.setAttribute("error", "Login ou mot de passe incorrect");
                getServletContext().getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (DAOException e) {
            e.printStackTrace();
            response.sendError(500, "Erreur interne du serveur");
        }
    }
}
