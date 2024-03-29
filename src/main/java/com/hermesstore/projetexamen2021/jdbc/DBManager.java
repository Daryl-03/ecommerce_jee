package com.hermes.store.projetexamen2023.jdbc;

import com.hermes.store.projetexamen2023.exceptions.DBHandlingException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static Connection connection;
    
    private DBManager() {
    }
    
    public static Connection getConnection() throws DBHandlingException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/bdProjetExamen2023?zeroDateTimeBehavior=convertToNull", "root", "");
            return connection;
        } catch (ClassNotFoundException e) {
            throw new DBHandlingException("Driver Class not found : '" + e.getMessage() + "' ");
        } catch (SQLException e) {
            throw new DBHandlingException("Error : Unable to open connection with database !");
        }
    }
}
