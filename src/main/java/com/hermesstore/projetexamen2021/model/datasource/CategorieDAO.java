package com.hermesstore.projetexamen2021.model.datasource;

import com.hermesstore.projetexamen2021.exceptions.DAOException;
import com.hermesstore.projetexamen2021.jdbc.DBManager;
import com.hermesstore.projetexamen2021.model.Categorie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategorieDAO implements DAO<Categorie>{
    @Override
    public int create(Categorie obj) throws DAOException {
        return 0;
    }
    
    @Override
    public Categorie read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            String sql = "SELECT * FROM categorie WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Categorie(resultSet.getInt("id"), resultSet.getString("nom"), resultSet.getString("description"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to find category !");
        }
        return null;
    }
    
    @Override
    public void update(Categorie obj) throws DAOException {
    
    }
    
    @Override
    public void delete(int id) throws DAOException {
    
    }
    
    @Override
    public List<Categorie> readAll() throws DAOException {
        List<Categorie> categories = new ArrayList<>();
        try (Connection connection = DBManager.getConnection()){
            String sql = "SELECT * FROM categorie";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                categories.add(new Categorie(resultSet.getInt("id"), resultSet.getString("nom"), resultSet.getString("description")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to find categories !");
        }
        return categories;
    }
}
