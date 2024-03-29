package com.hermesstore.projetexamen2021.model.datasource;

import com.hermesstore.projetexamen2021.exceptions.DAOException;
import com.hermesstore.projetexamen2021.jdbc.DBManager;
import com.hermesstore.projetexamen2021.model.Commande;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAO implements DAO<Commande>{
    @Override
    public int create(Commande obj) throws DAOException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO commande (type, date, statut, prix, id_client) VALUES(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, obj.getType());
            statement.setDate(2, java.sql.Date.valueOf(obj.getDate()));
            statement.setString(3, obj.getEtat());
            statement.setDouble(4, obj.getPrix());
            statement.setInt(5, obj.getId_client());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la création d'une commande");
        }
        return 0;
    }
    
    @Override
    public Commande read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM commande WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Commande(
                        resultSet.getInt("id"),
                        resultSet.getString("type"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("statut"),
                        resultSet.getDouble("prix"),
                        resultSet.getInt("id_client"),
                        new LivraisonDAO().readAllByCommande(resultSet.getInt("id"))
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la lecture d'une commande");
        }
        return null;
    }
    
    @Override
    public void update(Commande obj) throws DAOException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE commande SET type = ?, date = ?, statut = ?, id_client = ? WHERE id = ?")) {
            statement.setString(1, obj.getType());
            statement.setDate(2, java.sql.Date.valueOf(obj.getDate()));
            statement.setString(3, obj.getEtat());
            statement.setInt(4, obj.getId_client());
            statement.setInt(5, obj.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la mise à jour d'une commande");
        }
    }
    
    @Override
    public void delete(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM commande WHERE id = ?")) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la suppression d'une commande");
        }
    }
    
    @Override
    public List<Commande> readAll() throws DAOException {
        List<Commande> commandes = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM commande");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                commandes.add(new Commande(
                    resultSet.getInt("id"),
                    resultSet.getString("type"),
                    resultSet.getDate("date").toLocalDate(),
                    resultSet.getString("statut"),
                    resultSet.getDouble("prix"),
                    resultSet.getInt("id_client"),
                    new LivraisonDAO().readAllByCommande(resultSet.getInt("id"))
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la lecture de toutes les commandes");
        }
        return commandes;
    }
    
    public List<Commande> readAllByClient(int id_client) throws DAOException {
        List<Commande> commandes = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM commande WHERE id_client = ?")) {
            statement.setInt(1, id_client);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    commandes.add(new Commande(
                        resultSet.getInt("id"),
                        resultSet.getString("type"),
                        resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("statut"),
                        resultSet.getDouble("prix"),
                        resultSet.getInt("id_client"),
                        new LivraisonDAO().readAllByCommande(resultSet.getInt("id"))
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la lecture de toutes les commandes d'un client");
        }
        return commandes;
    }
}
