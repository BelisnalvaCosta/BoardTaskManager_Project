package br.com.dio.board.service;

import br.com.dio.board.entity.CardEntity;

import java.sql.Connection;
import java.util.Optional;

public class CardQueryService {

    private final Connection connection;

    public CardQueryService(Connection connection) {
        this.connection = connection;
    }

    public Optional<CardEntity> findById(Long cardId) {
        try {
            var stmt = connection.prepareStatement("SELECT * FROM cards WHERE id = ?");
            stmt.setLong(1, cardId);
            var rs = stmt.executeQuery();
            if (!rs.next()) return Optional.empty();

            var card = new CardEntity();
            card.setId(rs.getLong("id"));
            card.setTitle(rs.getString("title"));
            card.setDescription(rs.getString("description"));
            card.setBlocked(rs.getBoolean("blocked"));
            card.setBlockReason(rs.getString("block_reason"));
            card.setBlocksAmount(rs.getInt("blocks_amount"));
            card.setColumnId(rs.getLong("column_id"));

            // Buscar nome da coluna
            var colStmt = connection.prepareStatement("SELECT name FROM board_columns WHERE id = ?");
            colStmt.setLong(1, card.getColumnId());
            var colRS = colStmt.executeQuery();
            if (colRS.next()) {
                card.setBoardColumnName(colRS.getString("name"));
            }

            return Optional.of(card);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
