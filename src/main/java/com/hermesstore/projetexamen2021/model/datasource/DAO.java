package com.hermesstore.projetexamen2021.model.datasource;

import com.hermesstore.projetexamen2021.exceptions.DAOException;

import java.util.List;

public interface DAO<T> {
    public int create(T obj) throws DAOException;
    public T read(int id) throws DAOException;
    public void update(T obj) throws DAOException;
    public void delete(int id) throws DAOException;
    public List<T> readAll() throws DAOException;
}
