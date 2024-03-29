package com.hermesstore.projetexamen2021.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Commande {
    private int id;
    private String type;
    private LocalDate date;
    private String etat;
    private double prix;
    private int id_client;
    private List<Livraison> livraisons;
    
    public Commande() {
        livraisons = new ArrayList<>();
        type = "Livraison";
        etat = "En cours";
        date = LocalDate.now();
    }
    
    public Commande(int id, String type, LocalDate date, String etat, double prix, int id_client, List<Livraison> livraisons) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.etat = etat;
        this.prix = prix;
        this.id_client = id_client;
        this.livraisons = livraisons;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public String getEtat() {
        return etat;
    }
    
    public void setEtat(String etat) {
        this.etat = etat;
    }
    
    public int getId_client() {
        return id_client;
    }
    
    public void setId_client(int id_client) {
        this.id_client = id_client;
    }
    
    public List<Livraison> getLivraisons() {
        return livraisons;
    }
    
    public void setLivraisons(List<Livraison> livraisons) {
        this.livraisons = livraisons;
    }
    
    public double getPrix() {
        return prix;
    }
    
    public void setPrix(double prix) {
        this.prix = prix;
    }
}
