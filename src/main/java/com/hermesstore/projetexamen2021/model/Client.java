package com.hermesstore.projetexamen2021.model;

public class Client extends Utilisateur{
    private String nom;
    private String prenom;
    private String type;
    private String adresse;
    private String telephone;
    private String specialite;
    
    public Client() {
        super();
        this.profil = "Client";
    }
    
    public Client(int id, String login, String password, String nom, String prenom, String type, String adresse, String telephone, String specialite) {
        super(id, login, password);
        this.nom = nom;
        this.prenom = prenom;
        this.profil = "Client";
        this.type = type;
        this.adresse = adresse;
        this.telephone = telephone;
        this.specialite = specialite;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getAdresse() {
        return adresse;
    }
    
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public String getSpecialite() {
        return specialite;
    }
    
    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }
}
