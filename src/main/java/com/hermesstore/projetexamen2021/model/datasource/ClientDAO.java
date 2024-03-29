package com.hermesstore.projetexamen2021.model.datasource;

import com.hermes.store.projetexamen2023.exceptions.DAOException;
import com.hermes.store.projetexamen2023.jdbc.DBManager;
import com.hermes.store.projetexamen2023.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ClientDAO implements DAO<Client>{
    @Override
    public int create(Client obj) throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            String sql = "INSERT INTO client (nom, prenom, type, adresse, telephone, specialite, id_user) VALUES (?, ?, ?, ?, ?, ?,?)";
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, obj.getNom());
            statement.setString(2, obj.getPrenom());
            statement.setString(3, obj.getType());
            statement.setString(4, obj.getAdresse());
            statement.setString(5, obj.getTelephone());
            statement.setString(6, obj.getSpecialite());
            statement.setInt(7, new UtilisateurDAO().create(obj));
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
        return null;
    }
    
    @Override
    public void update(Client obj) throws DAOException {
    
    }
    
    @Override
    public void delete(Client obj) throws DAOException {
    
    }
    
    @Override
    public List<Client> readAll() throws DAOException {
        return null;
    }
}
