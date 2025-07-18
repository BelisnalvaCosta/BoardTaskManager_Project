package br.com.dio.board.service;

import br.com.dio.board.entity.BoardEntity;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Optional;

public class BoardQueryService {
    private final Connection connection;

    public BoardQueryService(Connection connection) {
        this.connection = connection;
    }

    public Optional<BoardEntity> findById(Long id) {
        try {
            var stmt = connection.prepareStatement("SELECT * FROM boards WHERE id = ?");
            stmt.setLong(1, id);
            var rs = stmt.executeQuery();
            if (!rs.next()) return Optional.empty();

            var entity = new BoardEntity();
            entity.setId(rs.getLong("id"));
            entity.setName(rs.getString("name"));
            entity.setBoardColumns(new BoardColumnQueryService(connection).findByBoardId(id));
            return Optional.of(entity);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<BoardEntity> showBoardDetails(Long boardId) {
        return findById(boardId);
    }
}