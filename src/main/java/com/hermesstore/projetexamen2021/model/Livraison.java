package com.hermesstore.projetexamen2021.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Livraison {
    private int code;
    private LocalDate date;
    private String adresse;
    private String telephone;
    private String etat;
    private int id_cmd;
    private int id_fournisseur;
    private List<ProduitCmd> produits;
    
    public Livraison() {
        produits = new ArrayList<>();
    }
    
    public Livraison(int code, LocalDate date, String adresse, String telephone, String etat, int id_cmd, int id_fournisseur, List<ProduitCmd> produits) {
        this.code = code;
        this.date = date;
        this.adresse = adresse;
        this.telephone = telephone;
        this.etat = etat;
        this.id_cmd = id_cmd;
        this.id_fournisseur = id_fournisseur;
        this.produits = produits;
    }
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public String getAdresse() {
        return adresse;
    }
    
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    public String getEtat() {
        return etat;
    }
    
    public void setEtat(String etat) {
        this.etat = etat;
    }
    
    public int getId_cmd() {
        return id_cmd;
    }
    
    public void setId_cmd(int id_cmd) {
        this.id_cmd = id_cmd;
    }
    
    public List<ProduitCmd> getProduits() {
        return produits;
    }
    
    public void setProduits(List<ProduitCmd> produits) {
        this.produits = produits;
    }
    
    public int getId_fournisseur() {
        return id_fournisseur;
    }
    
    public void setId_fournisseur(int id_fournisseur) {
        this.id_fournisseur = id_fournisseur;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
