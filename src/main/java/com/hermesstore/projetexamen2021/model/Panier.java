package com.hermesstore.projetexamen2021.model;

import java.util.ArrayList;
import java.util.List;

public class Panier {
    private int id;
    private int id_client;
    private List<ProduitPanier> produits;
    private double prix;
    
    public Panier() {
        produits = new ArrayList<>();
    }
    
    public Panier(int id, int id_client, List<ProduitPanier> produits, double prix) {
        this.id = id;
        this.id_client = id_client;
        this.produits = produits;
        this.prix = getPrix();
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId_client() {
        return id_client;
    }
    
    public void setId_client(int id_client) {
        this.id_client = id_client;
    }
    
    public List<ProduitPanier> getProduits() {
        return produits;
    }
    
    public void setProduits(List<ProduitPanier> produits) {
        this.produits = produits;
    }
    
    public double getPrix() {
        // calculer le prix
        prix = 0;
        for (ProduitPanier produitPanier : produits) {
            prix += produitPanier.getQuantite() * produitPanier.getProduit().getPrixUnitaire();
        }
        return prix;
    }
    
    public void setPrix(double prix) {
        this.prix = prix;
    }
    
    public ProduitPanier existProduit(int id_produit) {
        for (ProduitPanier produitPanier : produits) {
            if (produitPanier.getProduit().getId() == id_produit) {
                return produitPanier;
            }
        }
        return null;
    }
    
    /**
     * Vérifier que les quantités des produits sont disponibles
     * si la quantité est disponible, on garde la quantité
     * sinon on garde la quantité disponible.
     * Si la quantité est 0, on supprime le produit du panier
     */
    public void refresh(){
        for (ProduitPanier produitPanier : produits) {
            double quantiteDisponible = produitPanier.getProduit().getQuantite();
            if (quantiteDisponible == 0) {
                produits.remove(produitPanier);
            } else if (produitPanier.getQuantite() > quantiteDisponible) {
                produitPanier.setQuantite(quantiteDisponible);
            }
        }
    }
    
    @Override
    public String toString() {
        return "Panier{" +
                "id=" + id +
                ", id_client=" + id_client +
                ", produits=" + produits +
                ", prix=" + prix +
                '}';
    }
}
