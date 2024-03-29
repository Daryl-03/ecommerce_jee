package com.hermesstore.projetexamen2021.model;

import java.time.LocalDate;

public class Produit {
    private int id;
    private String nom;
    private String image;
    private double quantite;
    private double prixUnitaire;
    private Categorie categorie;
    private LocalDate dateFabrication;
    private LocalDate dateExpiration;
    private int idFournisseur;
    
    public Produit() {
    }
    
    public Produit(int id, String nom, String image, double quantite, double prixUnitaire, Categorie categorie, LocalDate dateFabrication, LocalDate dateExpiration, int idFournisseur) {
        this.id = id;
        this.nom = nom;
        this.image = image;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.categorie = categorie;
        this.dateFabrication = dateFabrication;
        this.dateExpiration = dateExpiration;
        this.idFournisseur = idFournisseur;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public double getQuantite() {
        return quantite;
    }
    
    public void setQuantite(double quantite) {
        if(quantite < 0)
            throw new IllegalArgumentException("La quantité ne peut pas être négative");
        this.quantite = quantite;
    }
    
    public double getPrixUnitaire() {
        return prixUnitaire;
    }
    
    public void setPrixUnitaire(double prixUnitaire) {
        if(prixUnitaire < 0)
            throw new IllegalArgumentException("Le prix ne peut pas être négatif");
        this.prixUnitaire = prixUnitaire;
    }
    
    public Categorie getCategorie() {
        return categorie;
    }
    
    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
    
    public LocalDate getDateFabrication() {
        return dateFabrication;
    }
    
    public void setDateFabrication(LocalDate dateFabrication) {
        this.dateFabrication = dateFabrication;
    }
    
    public LocalDate getDateExpiration() {
        return dateExpiration;
    }
    
    public void setDateExpiration(LocalDate dateExpiration) {
        this.dateExpiration = dateExpiration;
    }
    
    public int getIdFournisseur() {
        return idFournisseur;
    }
    
    public void setIdFournisseur(int idFournisseur) {
        this.idFournisseur = idFournisseur;
    }
    
    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", image='" + image + '\'' +
                ", quantite=" + quantite +
                ", prixUnitaire=" + prixUnitaire +
                ", categorie=" + categorie +
                ", dateFabrication=" + dateFabrication +
                ", dateExpiration=" + dateExpiration +
                ", idFournisseur=" + idFournisseur +
                '}';
    }
}
