package com.hermesstore.projetexamen2021.model.datasource;

import com.hermesstore.projetexamen2021.exceptions.DAOException;
import com.hermesstore.projetexamen2021.jdbc.DBManager;
import com.hermesstore.projetexamen2021.model.ProduitPanier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProduitPanierDAO implements DAO<ProduitPanier> {
    
    @Override
    public int create(ProduitPanier obj) throws DAOException {
        try(Connection connection = DBManager.getConnection()){
            String sql = "INSERT INTO produitpanier (id_produit, quantite, id_panier) VALUES(?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, obj.getProduit().getId());
            statement.setDouble(2, obj.getQuantite());
            statement.setInt(3, obj.getId_panier());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la création d'un produit dans un panier");
        }
        return 0;
    }
    
    @Override
    public ProduitPanier read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM produitpanier WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new ProduitPanier(resultSet.getInt("id"),
                    new ProduitDAO().read(resultSet.getInt("id_produit")),
                    resultSet.getInt("quantite"),
                    resultSet.getInt("id_panier"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la lecture d'un produit dans un panier");
        }
        return null;
    }
    
    @Override
    public void update(ProduitPanier obj) throws DAOException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE produitpanier SET id_produit = ?, quantite = ?, id_panier = ? WHERE id = ?")) {
            statement.setInt(1, obj.getProduit().getId());
            statement.setDouble(2, obj.getQuantite());
            statement.setInt(3, obj.getId_panier());
            statement.setInt(4, obj.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la mise à jour d'un produit dans un panier");
        }
    }
    
    @Override
    public void delete(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM produitpanier WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la suppression d'un produit dans un panier");
        }
    }
    
    @Override
    public List<ProduitPanier> readAll() throws DAOException {
        List<ProduitPanier> produitPaniers = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM produitpanier")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    produitPaniers.add(new ProduitPanier(resultSet.getInt("id"),
                    new ProduitDAO().read(resultSet.getInt("id_produit")),
                    resultSet.getInt("quantite"),
                    resultSet.getInt("id_panier")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la lecture de tous les produits dans un panier");
        }
        return produitPaniers;
    }
    
    public List<ProduitPanier> readAllByPanier(int id_panier) throws DAOException {
        List<ProduitPanier> produitPaniers = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM produitpanier WHERE id_panier = ?")) {
            statement.setInt(1, id_panier);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    produitPaniers.add(new ProduitPanier(resultSet.getInt("id"),
                    new ProduitDAO().read(resultSet.getInt("id_produit")),
                    resultSet.getInt("quantite"),
                    resultSet.getInt("id_panier")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la lecture de tous les produits dans un panier");
        }
        return produitPaniers;
    }
    
    public void deleteByPanier(int id_panier) throws DAOException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM produitpanier WHERE id_panier = ?")) {
            statement.setInt(1, id_panier);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la suppression d'un produit dans un panier");
        }
    }
}
