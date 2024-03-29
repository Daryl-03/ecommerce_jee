package com.hermesstore.projetexamen2021.model.datasource;

import com.hermesstore.projetexamen2021.exceptions.DAOException;
import com.hermesstore.projetexamen2021.jdbc.DBManager;
import com.hermesstore.projetexamen2021.model.Panier;
import com.hermesstore.projetexamen2021.model.ProduitPanier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class PanierDAO implements DAO<Panier>{
    @Override
    public int create(Panier obj) throws DAOException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO panier (prix, id_client) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setDouble(1, obj.getPrix());
            statement.setInt(2, obj.getId_client());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la création d'un panier");
        }
        return 0;
    }
    
    @Override
    public Panier read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM panier WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Panier(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_client"),
                        new ProduitPanierDAO().readAllByPanier(resultSet.getInt("id")),
                        resultSet.getDouble("prix")
                        );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la lecture d'un panier");
        }
        return null;
    }
    
    @Override
    public void update(Panier obj) throws DAOException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE panier SET prix = ?, id_client = ? WHERE id = ?")) {
            statement.setDouble(1, obj.getPrix());
            statement.setInt(2, obj.getId_client());
            statement.setInt(3, obj.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la mise à jour d'un panier");
        }
    }
    
    @Override
    public void delete(int id) throws DAOException {
    
    }
    
    @Override
    public List<Panier> readAll() throws DAOException {
        return null;
    }
    
    public void emptyPanier(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM produitpanier WHERE id_panier = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la suppression d'un panier");
        }
    }
    
    public Panier readByClient(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM panier WHERE id_client = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Panier(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_client"),
                        new ProduitPanierDAO().readAllByPanier(resultSet.getInt("id")),
                        resultSet.getDouble("prix")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la lecture d'un panier");
        }
        return null;
    }
    
    /**
     * Mets à jour le prix du panier depuis la base de données en fonction de la somme des prix des produits et la quantité
     * @param id
     */
    public void updatePrix(int id) throws DAOException {
        try {
            Panier panier = read(id);
            update(panier);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la mise à jour du prix d'un panier");
        }
    }
}
