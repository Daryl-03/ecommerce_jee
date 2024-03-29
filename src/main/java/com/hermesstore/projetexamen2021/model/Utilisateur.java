package com.hermes.store.projetexamen2023.model;

public class Utilisateur {
    protected int id;
    protected String login;
    protected String password;
    protected String profil;
    
    public Utilisateur() {
    }
    
    public Utilisateur(int id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }
    
    public Utilisateur(int id, String login, String password, String profil) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.profil = profil;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getProfil() {
        return profil;
    }
    
    public void setProfil(String profil) {
        this.profil = profil;
    }
}
