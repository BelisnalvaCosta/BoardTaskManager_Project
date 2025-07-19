package br.com.dio.board.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDatabase {
    public static void initialize() throws SQLException {
        try (Connection conn = ConnectionConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS board (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS board_column (id INTEGER PRIMARY KEY AUTOINCREMENT, board_id INTEGER NOT NULL, name TEXT NOT NULL, kind TEXT NOT NULL, col_order INTEGER NOT NULL, FOREIGN KEY (board_id) REFERENCES board(id) ON DELETE CASCADE)");
        }
    }
}