package com.hermesstore.projetexamen2021.model.datasource;

import com.hermes.store.projetexamen2023.exceptions.DAOException;
import com.hermes.store.projetexamen2023.jdbc.DBManager;
import com.hermes.store.projetexamen2023.model.Fournisseur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class FournisseurDAO implements DAO<Fournisseur>{
    @Override
    public int create(Fournisseur obj) throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            String sql = "INSERT INTO fournisseur (nom, prenom, adresse, nationalite, telephone, id_user) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, obj.getNom());
            statement.setString(2, obj.getPrenom());
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
    
    @Override
    public Fournisseur read(int id) throws DAOException {
        return null;
    }
    
    @Override
    public void update(Fournisseur obj) throws DAOException {
    
    }
    
    @Override
    public void delete(Fournisseur obj) throws DAOException {
    
    }
    
    @Override
    public List<Fournisseur> readAll() throws DAOException {
        return null;
    }
}
