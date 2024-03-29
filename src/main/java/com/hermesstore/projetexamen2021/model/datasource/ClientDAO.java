package com.hermesstore.projetexamen2021.model.datasource;

import com.hermesstore.projetexamen2021.exceptions.DAOException;
import com.hermesstore.projetexamen2021.jdbc.DBManager;
import com.hermesstore.projetexamen2021.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ClientDAO implements DAO<Client>{
    @Override
    public int create(Client obj) throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            String sql = "INSERT INTO client (nom, type, adresse, telephone, specialite, id_user) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, obj.getNom());
            statement.setString(2, obj.getType());
            statement.setString(3, obj.getAdresse());
            statement.setString(4, obj.getTelephone());
            statement.setString(5, obj.getSpecialite());
            statement.setInt(6, new UtilisateurDAO().create(obj));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            } else {
                throw new DAOException("Error : Unable to create client !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to create client !");
        }
    }
    
    @Override
    public Client read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            String sql = "SELECT * FROM client JOIN utilisateur ON client.id_user = utilisateur.id WHERE client.id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Client client =  new Client(
                        resultSet.getInt("client.id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("nom"),
                        resultSet.getString("type"),
                        resultSet.getString("adresse"),
                        resultSet.getString("telephone"),
                        resultSet.getString("specialite")
                );
                client.setProfil("Client");
                return client;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to read client !");
        }
        return null;
    }
    
    @Override
    public void update(Client obj) throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            new UtilisateurDAO().updateClient(obj);
            String sql = "UPDATE client SET nom = ?, type = ?, adresse = ?, telephone = ?, specialite = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, obj.getNom());
            statement.setString(2, obj.getType());
            statement.setString(3, obj.getAdresse());
            statement.setString(4, obj.getTelephone());
            statement.setString(5, obj.getSpecialite());
            statement.setInt(6, obj.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to update client !");
        }
    }
    
    @Override
    public void delete(int id) throws DAOException {
    
    }
    
    @Override
    public List<Client> readAll() throws DAOException {
        return null;
    }
    
    public Client readByLoginAndPassword(String login, String password) throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            String sql = "SELECT * FROM client JOIN utilisateur ON client.id_user = utilisateur.id WHERE login = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Client client =  new Client(
                        resultSet.getInt("client.id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("nom"),
                        resultSet.getString("type"),
                        resultSet.getString("adresse"),
                        resultSet.getString("telephone"),
                        resultSet.getString("specialite")
                );
                client.setProfil("Client");
                return client;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to read client !");
        }
        return null;
    }
}
