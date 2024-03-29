package com.hermesstore.projetexamen2021.model.datasource;

import com.hermesstore.projetexamen2021.exceptions.DAOException;
import com.hermesstore.projetexamen2021.jdbc.DBManager;
import com.hermesstore.projetexamen2021.model.Fournisseur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class FournisseurDAO implements DAO<Fournisseur>{
    @Override
    public int create(Fournisseur obj) throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            String sql = "INSERT INTO fournisseur (code, nom, adresse, nationalite, telephone, id_user) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            obj.setCode(generateCodeFournisseur());
            statement.setString(1, obj.getCode());
            statement.setString(2, obj.getNom());
            statement.setString(3, obj.getAdresse());
            statement.setString(4, obj.getNationalite());
            statement.setString(5, obj.getTelephone());
            statement.setInt(6, new UtilisateurDAO().create(obj));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new DAOException("Error : Unable to create supplier !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to create supplier !");
        }
    }
    
    public String generateCodeFournisseur() throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            String sql = "SELECT MAX(id) FROM fournisseur";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return "F" + String.format("%04d", resultSet.getInt(1) + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to generate supplier code !");
        }
        return null;
    }
    
    @Override
    public Fournisseur read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            String sql = "SELECT * FROM fournisseur JOIN utilisateur ON fournisseur.id_user = utilisateur.id WHERE fournisseur.id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Fournisseur fournisseur =  new Fournisseur(
                        resultSet.getInt("fournisseur.id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("code"),
                        resultSet.getString("nom"),
                        resultSet.getString("adresse"),
                        resultSet.getString("nationalite"),
                        resultSet.getString("telephone")
                );
                fournisseur.setProfil("Fournisseur");
                return fournisseur;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to read supplier !");
        }
        return null;
    }
    
    @Override
    public void update(Fournisseur obj) throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            new UtilisateurDAO().updateFournisseur(obj);
            String sql = "UPDATE fournisseur SET code = ?, nom = ?, adresse = ?, nationalite = ?, telephone = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, obj.getCode());
            statement.setString(2, obj.getNom());
            statement.setString(3, obj.getAdresse());
            statement.setString(4, obj.getNationalite());
            statement.setString(5, obj.getTelephone());
            statement.setInt(6, obj.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to update supplier !");
        }
    }
    
    @Override
    public void delete(int id) throws DAOException {
    
    }
    
    @Override
    public List<Fournisseur> readAll() throws DAOException {
        return null;
    }
    
    public Fournisseur readByLoginAndPassword(String login, String password) throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            String sql = "SELECT * FROM fournisseur JOIN utilisateur ON fournisseur.id_user = utilisateur.id WHERE login = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Fournisseur fournisseur =  new Fournisseur(
                        resultSet.getInt("fournisseur.id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("code"),
                        resultSet.getString("nom"),
                        resultSet.getString("adresse"),
                        resultSet.getString("nationalite"),
                        resultSet.getString("telephone")
                );
                fournisseur.setProfil("Fournisseur");
                return fournisseur;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to read supplier !");
        }
        return null;
    }
}
