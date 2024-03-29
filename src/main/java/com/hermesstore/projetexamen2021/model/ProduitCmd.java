package com.hermesstore.projetexamen2021.model;

public class ProduitCmd {
    private int id;
    private Produit produit;
    private double quantite;
    private int id_livraison;
    
    public ProduitCmd() {
    }
    
    public ProduitCmd(int id, Produit produit, int quantite, int id_livraison) {
        this.id = id;
        this.produit = produit;
        this.quantite = quantite;
        this.id_livraison = id_livraison;
    }
    
    public ProduitCmd(ProduitPanier produitPanier) {
        this.produit = produitPanier.getProduit();
        this.quantite = produitPanier.getQuantite();
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public Produit getProduit() {
        return produit;
    }
    
    public void setProduit(Produit produit) {
        this.produit = produit;
    }
    
    public double getQuantite() {
        return quantite;
    }
    
    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }
    
    public int getId_livraison() {
        return id_livraison;
    }
    
    public void setId_livraison(int id_livraison) {
        this.id_livraison = id_livraison;
    }
}
