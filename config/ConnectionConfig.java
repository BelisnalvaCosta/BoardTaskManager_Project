package br.com.dio.board.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionConfig {
    private static final String DB_URL = "jdbc:sqlite:board.db"; // Arquivo na raiz do projeto

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}