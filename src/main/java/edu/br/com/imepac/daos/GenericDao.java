package edu.br.com.imepac.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GenericDao {
    private static final String URL = "jdbc:mysql://localhost:3306/message-manager";
    private static final String USER = "message-manager";
    private static final String PASSWORD = "12345678";

    private Connection connection;

    protected Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar com o banco de dados", e);
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}