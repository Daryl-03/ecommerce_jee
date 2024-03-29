package com.hermesstore.projetexamen2021.model.datasource;

import com.hermesstore.projetexamen2021.exceptions.DAOException;
import com.hermesstore.projetexamen2021.jdbc.DBManager;
import com.hermesstore.projetexamen2021.model.ProduitCmd;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitCmdDAO implements DAO<ProduitCmd> {
    @Override
    public int create(ProduitCmd obj) throws DAOException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO produitlivraison (id_produit, quantite, id_livraison) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, obj.getProduit().getId());
            statement.setDouble(2, obj.getQuantite());
            statement.setInt(3, obj.getId_livraison());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la création d'un produit_cmd");
        }
        return 0;
    }
    
    @Override
    public ProduitCmd read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM produitlivraison WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new ProduitCmd(
                        resultSet.getInt("id"),
                        new ProduitDAO().read(resultSet.getInt("id_produit")),
                        resultSet.getInt("quantite"), resultSet.getInt("id_livraison"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la lecture d'un produit_cmd");
        }
        return null;
    }
    
    @Override
    public void update(ProduitCmd obj) throws DAOException {
    
    }
    
    @Override
    public void delete(int id) throws DAOException {
    
    }
    
    @Override
    public List<ProduitCmd> readAll() throws DAOException {
        List<ProduitCmd> produitCmds = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM produitlivraison")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    produitCmds.add(new ProduitCmd(
                        resultSet.getInt("id"),
                        new ProduitDAO().read(resultSet.getInt("id_produit")),
                        resultSet.getInt("quantite"), resultSet.getInt("id_livraison")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la lecture d'un produit_cmd");
        }
        return produitCmds;
    }
    
    public List<ProduitCmd> readAllByLivraison(int idLivraison) throws DAOException {
        List<ProduitCmd> produitCmds = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM produitlivraison WHERE id_livraison = ?")) {
            statement.setInt(1, idLivraison);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    produitCmds.add(new ProduitCmd(
                        resultSet.getInt("id"),
                        new ProduitDAO().read(resultSet.getInt("id_produit")),
                        resultSet.getInt("quantite"), resultSet.getInt("id_livraison")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la lecture d'un produit_cmd");
        }
        return produitCmds;
    }
}
