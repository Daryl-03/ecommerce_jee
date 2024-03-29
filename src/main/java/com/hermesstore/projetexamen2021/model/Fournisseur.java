package com.hermesstore.projetexamen2021.model;

public class Fournisseur extends Utilisateur{
    private String code;
    private String nom;
    private String prenom;
    private String adresse;
    private String nationalite;
    private String telephone;
    
    public Fournisseur() {
        super();
        this.profil = "Fournisseur";
    }
    
    public Fournisseur(int id, String login, String password, String code, String nom, String prenom, String adresse, String nationalite, String telephone) {
        super(id, login, password);
        this.profil = "Fournisseur";
        this.code = code;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.nationalite = nationalite;
        this.telephone = telephone;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
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
    
    public String getAdresse() {
        return adresse;
    }
    
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    public String getNationalite() {
        return nationalite;
    }
    
    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
