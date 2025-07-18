package br.com.dio.board.service;

import br.com.dio.board.entity.BoardColumnEntity;
import br.com.dio.board.entity.BoardEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class BoardService {
    private final Connection connection;

    public BoardService(Connection connection) {
        this.connection = connection;
    }

    public void insert(BoardEntity entity) {
        try {
            var pstmt = connection.prepareStatement("INSERT INTO boards (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, entity.getName());
            pstmt.executeUpdate();

            var rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                entity.setId(rs.getLong(1));
            }

            for (BoardColumnEntity col : entity.getBoardColumns()) {
                var colStmt = connection.prepareStatement("INSERT INTO board_columns (name, kind, \"order\", board_id) VALUES (?, ?, ?, ?)");
                colStmt.setString(1, col.getName());
                colStmt.setString(2, col.getKind().name());
                colStmt.setInt(3, col.getOrder());
                colStmt.setLong(4, entity.getId());
                colStmt.executeUpdate();
            }

            System.out.println("Board criado com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean delete(Long boardId) {
        try {
            var deleteCards = connection.prepareStatement("DELETE FROM cards WHERE column_id IN (SELECT id FROM board_columns WHERE board_id = ?)");
            deleteCards.setLong(1, boardId);
            deleteCards.executeUpdate();

            var deleteColumns = connection.prepareStatement("DELETE FROM board_columns WHERE board_id = ?");
            deleteColumns.setLong(1, boardId);
            deleteColumns.executeUpdate();

            var deleteBoard = connection.prepareStatement("DELETE FROM boards WHERE id = ?");
            deleteBoard.setLong(1, boardId);
            return deleteBoard.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}