package com.hermes.store.projetexamen2023.model.datasource;

import com.hermes.store.projetexamen2023.exceptions.DAOException;

import java.util.List;

public interface DAO<T> {
    public int create(T obj) throws DAOException;
    public T read(int id) throws DAOException;
    public void update(T obj) throws DAOException;
    public void delete(T obj) throws DAOException;
    public List<T> readAll() throws DAOException;
}
