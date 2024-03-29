package com.hermesstore.projetexamen2021.model;

public class ProduitPanier {
    private int id;
    private Produit produit;
    private double quantite;
    private int id_panier;
    
    public ProduitPanier() {
    }
    
    public ProduitPanier(int id, Produit produit, int quantite, int id_panier) {
        this.id = id;
        this.produit = produit;
        this.quantite = quantite;
        this.id_panier = id_panier;
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
    
    public int getId_panier() {
        return id_panier;
    }
    
    public void setId_panier(int id_panier) {
        this.id_panier = id_panier;
    }
    
    @Override
    public String toString() {
        return "ProduitPanier{" +
                "id=" + id +
                ", produit=" + produit +
                ", quantite=" + quantite +
                ", id_panier=" + id_panier +
                '}';
    }
}
