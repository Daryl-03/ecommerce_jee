package com.hermesstore.projetexamen2021.model.datasource;

import com.hermesstore.projetexamen2021.exceptions.DAOException;
import com.hermesstore.projetexamen2021.jdbc.DBManager;
import com.hermesstore.projetexamen2021.model.Livraison;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LivraisonDAO implements DAO<Livraison>{
    @Override
    public int create(Livraison obj) throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            String sql = "INSERT INTO livraison (date, adresse, telephone, id_commande, id_fournisseur) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if(obj.getDate() == null)
                statement.setNull(1, java.sql.Types.DATE);
            else
                statement.setDate(1, java.sql.Date.valueOf(obj.getDate()));
            statement.setString(2, obj.getAdresse());
            statement.setString(3, obj.getTelephone());
            statement.setInt(4, obj.getId_cmd());
            statement.setInt(5, obj.getId_fournisseur());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la création d'une livraison");
        }
        return 0;
    }
    
    @Override
    public Livraison read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM livraison WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Livraison(
                        resultSet.getInt("id"),
                        resultSet.getDate("date")==null?null:resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("adresse"),
                        resultSet.getString("telephone"),
                        resultSet.getString("etat"),
                        resultSet.getInt("id_commande"),
                        resultSet.getInt("id_fournisseur"),
                        new ProduitCmdDAO().readAllByLivraison(resultSet.getInt("id"))
                        );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la lecture d'une livraison");
        }
        return null;
    }
    
    @Override
    public void update(Livraison obj) throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            String sql = "UPDATE livraison SET date = ?, adresse = ?, etat = ?, id_commande = ?, id_fournisseur = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, java.sql.Date.valueOf(obj.getDate()));
            statement.setString(2, obj.getAdresse());
            statement.setString(3, obj.getEtat());
            statement.setInt(4, obj.getId_cmd());
            statement.setInt(5, obj.getId_fournisseur());
            statement.setInt(6, obj.getCode());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la mise à jour d'une livraison");
        }
    }
    
    @Override
    public void delete(int id) throws DAOException {
    
    }
    
    @Override
    public List<Livraison> readAll() throws DAOException {
        List<Livraison> livraisons = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM livraison")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    livraisons.add(new Livraison(
                        resultSet.getInt("id"),
                        resultSet.getDate("date")==null?null:resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("adresse"),
                        resultSet.getString("telephone"),
                        resultSet.getString("etat"),
                        resultSet.getInt("id_commande"),
                        resultSet.getInt("id_fournisseur"),
                        new ProduitCmdDAO().readAllByLivraison(resultSet.getInt("id"))
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la lecture de toutes les livraisons");
        }
        return livraisons;
    }
    
    public List<Livraison> readAllByCommande(int id_cmd) throws DAOException {
        List<Livraison> livraisons = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM livraison WHERE id_commande = ?")) {
            statement.setInt(1, id_cmd);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    livraisons.add(new Livraison(
                        resultSet.getInt("id"),
                        resultSet.getDate("date")==null?null:resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("adresse"),
                        resultSet.getString("telephone"),
                        resultSet.getString("etat"),
                        resultSet.getInt("id_commande"),
                        resultSet.getInt("id_fournisseur"),
                        new ProduitCmdDAO().readAllByLivraison(resultSet.getInt("id"))
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la lecture de toutes les livraisons");
        }
        return livraisons;
    }
    
    public List<Livraison> readAllByFournisseur(int id_fournisseur) throws DAOException {
        List<Livraison> livraisons = new ArrayList<>();
        try (Connection connection = DBManager.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM livraison WHERE id_fournisseur = ?")) {
            statement.setInt(1, id_fournisseur);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    livraisons.add(new Livraison(
                        resultSet.getInt("id"),
                        resultSet.getDate("date")==null?null:resultSet.getDate("date").toLocalDate(),
                        resultSet.getString("adresse"),
                        resultSet.getString("telephone"),
                        resultSet.getString("etat"),
                        resultSet.getInt("id_commande"),
                        resultSet.getInt("id_fournisseur"),
                        new ProduitCmdDAO().readAllByLivraison(resultSet.getInt("id"))
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Erreur lors de la lecture de toutes les livraisons");
        }
        return livraisons;
    }
}
