package com.hermesstore.projetexamen2021.model.datasource;

import com.hermesstore.projetexamen2021.exceptions.DAOException;
import com.hermesstore.projetexamen2021.jdbc.DBManager;
import com.hermesstore.projetexamen2021.model.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO implements DAO<Utilisateur>{
    @Override
    public int create(Utilisateur obj) throws DAOException {
        try(Connection connection = DBManager.getConnection()) {
            String sql = "INSERT INTO utilisateur (login, password, profil) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, obj.getLogin());
            statement.setString(2, obj.getPassword());
            statement.setString(3, obj.getProfil());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new DAOException("Error : Unable to create user !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to create user !");
        }
    }
    
    @Override
    public Utilisateur read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            String sql = "SELECT * FROM utilisateur WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(resultSet.getInt("id"));
                utilisateur.setLogin(resultSet.getString("login"));
                utilisateur.setPassword(resultSet.getString("password"));
                utilisateur.setProfil(resultSet.getString("profil"));
                return utilisateur;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to read user !");
        }
        return null;
    }
    
    @Override
    public void update(Utilisateur obj) throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            String sql = "UPDATE utilisateur SET login = ?, password = ?, profil = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, obj.getLogin());
            statement.setString(2, obj.getPassword());
            statement.setString(3, obj.getProfil());
            statement.setInt(4, obj.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to update user !");
        }
    }
    
    public void updateClient(Utilisateur obj) throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            String sql = "UPDATE utilisateur SET login = ?, password = ? WHERE id = (SELECT utilisateur.id FROM utilisateur JOIN client ON client.id_user = utilisateur.id WHERE client.id = ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, obj.getLogin());
            statement.setString(2, obj.getPassword());
            statement.setInt(3, obj.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to update user !");
        }
    }
    
    public void updateFournisseur(Utilisateur obj) throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            String sql = "UPDATE utilisateur SET login = ?, password = ? WHERE id = (SELECT utilisateur.id FROM utilisateur JOIN fournisseur ON fournisseur.id_user = utilisateur.id WHERE fournisseur.id = ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, obj.getLogin());
            statement.setString(2, obj.getPassword());
            statement.setInt(3, obj.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to update user !");
        }
    }
    
    @Override
    public void delete(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            String sql = "DELETE FROM utilisateur WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to delete user !");
        }
    }
    
    @Override
    public List<Utilisateur> readAll() throws DAOException {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()){
            String sql = "SELECT * FROM utilisateur";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(resultSet.getInt("id"));
                utilisateur.setLogin(resultSet.getString("login"));
                utilisateur.setPassword(resultSet.getString("password"));
                utilisateur.setProfil(resultSet.getString("profil"));
                utilisateurs.add(utilisateur);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to read all users !");
        }
        return utilisateurs;
    }
    
    /**
     * Method to check if the login(email) already exists in the database
     * @param login
     * @return true if the login already exists, false otherwise
     * @throws DAOException
     */
    public boolean loginExists(String login) throws DAOException {
        try (Connection connection = DBManager.getConnection()) {
            String sql = "SELECT * FROM utilisateur WHERE login = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to check if login exists !");
        }
        return false;
    }
    
    public Utilisateur readByLoginAndPassword(String login, String password) throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            String sql = "SELECT * FROM utilisateur WHERE login = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String profil = resultSet.getString("profil");
                switch (profil){
                    case "Fournisseur":
                        return new FournisseurDAO().readByLoginAndPassword(login, password);
                    case "Client":
                        return new ClientDAO().readByLoginAndPassword(login, password);
                    default:
                        return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to read user by login and password !");
        }
        return null;
    }
}
