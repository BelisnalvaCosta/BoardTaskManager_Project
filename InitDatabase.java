package br.com.dio.board;

import br.com.dio.board.config.ConnectionConfig;

import java.nio.file.Files;
import java.nio.file.Path;

public class InitDatabase {
    public static void initialize() {

        try (var connection = ConnectionConfig.getConnection();
             var statement = connection.createStatement()) {
            statement.executeUpdate(Files.readString(Path.of("schema.sql")));
            System.out.println("Banco de dados inicializado.");
        } catch (Exception e) {
            System.err.println("Erro ao inicializar banco: " + e.getMessage());
        }
    }
}
