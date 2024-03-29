package com.hermesstore.projetexamen2021.model.datasource;

import com.hermesstore.projetexamen2021.exceptions.DAOException;
import com.hermesstore.projetexamen2021.jdbc.DBManager;
import com.hermesstore.projetexamen2021.model.Produit;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO implements DAO<Produit>{
    @Override
    public int create(Produit obj) throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            String sql = "INSERT INTO produit (nom, image, quantite, prixUnitaire, id_categorie, dateFab, dateExp, id_fournisseur) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, obj.getNom());
            statement.setString(2, obj.getImage());
            statement.setDouble(3, obj.getQuantite());
            statement.setDouble(4, obj.getPrixUnitaire());
            statement.setInt(5, obj.getCategorie().getId());
            if(obj.getDateFabrication() != null)
                statement.setDate(6, Date.valueOf(obj.getDateFabrication()));
            else
                statement.setDate(6, null);
            if(obj.getDateExpiration() != null)
                statement.setDate(7, Date.valueOf(obj.getDateExpiration()));
            else
                statement.setDate(7, null);
            statement.setInt(8, obj.getIdFournisseur());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to create product !");
        }
        return 0;
    }
    
    @Override
    public Produit read(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            String sql = "SELECT * FROM produit WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Produit(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("image"),
                        resultSet.getDouble("quantite"),
                        resultSet.getDouble("prixUnitaire"),
                        new CategorieDAO().read(resultSet.getInt("id_categorie")),
                        resultSet.getDate("dateFab")!=null?resultSet.getDate("dateFab").toLocalDate():null,
                        resultSet.getDate("dateExp")!=null?resultSet.getDate("dateExp").toLocalDate():null,
                        resultSet.getInt("id_fournisseur")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to find product !");
        }
        return null;
    }
    
    @Override
    public void update(Produit obj) throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            String sql = "UPDATE produit SET nom = ?, image = ?, quantite = ?, prixUnitaire = ?, id_categorie = ?, dateFab = ?, dateExp = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, obj.getNom());
            statement.setString(2, obj.getImage());
            statement.setDouble(3, obj.getQuantite());
            statement.setDouble(4, obj.getPrixUnitaire());
            statement.setInt(5, obj.getCategorie().getId());
            if(obj.getDateFabrication() != null)
                statement.setDate(6, Date.valueOf(obj.getDateFabrication()));
            else
                statement.setDate(6, null);
            if(obj.getDateExpiration() != null)
                statement.setDate(7, Date.valueOf(obj.getDateExpiration()));
            else
                statement.setDate(7, null);
            statement.setInt(8, obj.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to update product !");
        }
    }
    
    @Override
    public void delete(int id) throws DAOException {
        try (Connection connection = DBManager.getConnection()){
            String sql = "DELETE FROM produit WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to delete product !");
        }
    }
    
    @Override
    public List<Produit> readAll() throws DAOException {
        List<Produit> produits = new ArrayList<>();
        try(Connection connection = DBManager.getConnection()){
            String sql = "SELECT * FROM produit";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                produits.add(new Produit(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("image"),
                        resultSet.getDouble("quantite"),
                        resultSet.getDouble("prixUnitaire"),
                        new CategorieDAO().read(resultSet.getInt("id_categorie")),
                        resultSet.getDate("dateFab")!=null?resultSet.getDate("dateFab").toLocalDate():null,
                        resultSet.getDate("dateExp")!=null?resultSet.getDate("dateExp").toLocalDate():null,
                        resultSet.getInt("id_fournisseur")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to find products !");
        }
        return produits;
    }
    
    public List<Produit> readAllByCategorie(int idCategorie) throws DAOException {
        List<Produit> produits = new ArrayList<>();
        try(Connection connection = DBManager.getConnection()){
            String sql = "SELECT * FROM produit WHERE id_categorie = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idCategorie);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                produits.add(new Produit(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("image"),
                        resultSet.getDouble("quantite"),
                        resultSet.getDouble("prixUnitaire"),
                        new CategorieDAO().read(resultSet.getInt("id_categorie")),
                        resultSet.getDate("dateFab")!=null?resultSet.getDate("dateFab").toLocalDate():null,
                        resultSet.getDate("dateExp")!=null?resultSet.getDate("dateExp").toLocalDate():null,
                        resultSet.getInt("id_fournisseur")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to find products !");
        }
        return produits;
    }
    
    public List<Produit> readAllByFournisseur(int idFournisseur) throws DAOException {
        List<Produit> produits = new ArrayList<>();
        try(Connection connection = DBManager.getConnection()){
            String sql = "SELECT * FROM produit WHERE id_fournisseur = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idFournisseur);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                produits.add(new Produit(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("image"),
                        resultSet.getDouble("quantite"),
                        resultSet.getDouble("prixUnitaire"),
                        new CategorieDAO().read(resultSet.getInt("id_categorie")),
                        resultSet.getDate("dateFab")!=null?resultSet.getDate("dateFab").toLocalDate():null,
                        resultSet.getDate("dateExp")!=null?resultSet.getDate("dateExp").toLocalDate():null,
                        resultSet.getInt("id_fournisseur")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to find products !");
        }
        return produits;
    }
    
    public List<Produit> readAllByCategorieAndFournisseur(int idCategorie, int idFournisseur) throws DAOException {
        List<Produit> produits = new ArrayList<>();
        try(Connection connection = DBManager.getConnection()){
            String sql = "SELECT * FROM produit WHERE id_categorie = ? AND id_fournisseur = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idCategorie);
            statement.setInt(2, idFournisseur);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                produits.add(new Produit(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("image"),
                        resultSet.getDouble("quantite"),
                        resultSet.getDouble("prixUnitaire"),
                        new CategorieDAO().read(resultSet.getInt("id_categorie")),
                        resultSet.getDate("dateFab")!=null?resultSet.getDate("dateFab").toLocalDate():null,
                        resultSet.getDate("dateExp")!=null?resultSet.getDate("dateExp").toLocalDate():null,
                        resultSet.getInt("id_fournisseur")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to find products !");
        }
        return produits;
    }
    
    public List<Produit> readAllByFournisseurAndName(int idFournisseur, String name) throws DAOException {
        List<Produit> produits = new ArrayList<>();
        try(Connection connection = DBManager.getConnection()){
            String sql = "SELECT * FROM produit WHERE id_fournisseur = ? AND nom LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idFournisseur);
            statement.setString(2, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                produits.add(new Produit(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("image"),
                        resultSet.getDouble("quantite"),
                        resultSet.getDouble("prixUnitaire"),
                        new CategorieDAO().read(resultSet.getInt("id_categorie")),
                        resultSet.getDate("dateFab")!=null?resultSet.getDate("dateFab").toLocalDate():null,
                        resultSet.getDate("dateExp")!=null?resultSet.getDate("dateExp").toLocalDate():null,
                        resultSet.getInt("id_fournisseur")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to find products !");
        }
        return produits;
    }
    
    public List<Produit> readAllByName(String name) throws DAOException {
        List<Produit> produits = new ArrayList<>();
        try(Connection connection = DBManager.getConnection()){
            String sql = "SELECT * FROM produit WHERE nom LIKE ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                produits.add(new Produit(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("image"),
                        resultSet.getDouble("quantite"),
                        resultSet.getDouble("prixUnitaire"),
                        new CategorieDAO().read(resultSet.getInt("id_categorie")),
                        resultSet.getDate("dateFab")!=null?resultSet.getDate("dateFab").toLocalDate():null,
                        resultSet.getDate("dateExp")!=null?resultSet.getDate("dateExp").toLocalDate():null,
                        resultSet.getInt("id_fournisseur")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException("Error : Unable to find products !");
        }
        return produits;
    }
}
