package br.com.dio.board.service;

import br.com.dio.board.dto.BoardColumnInfoDTO;
import br.com.dio.board.entity.CardEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class CardService {
    private final Connection connection;

    public CardService(Connection connection) {
        this.connection = connection;
    }

    public void create(CardEntity card) throws Exception {
        var sql = "INSERT INTO cards (title, description, column_id, blocked, block_reason, blocks_amount) VALUES (?, ?, ?, 0, '', 0)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, card.getTitle());
            stmt.setString(2, card.getDescription());
            stmt.setLong(3, card.getBoardColumn().getId());
            stmt.executeUpdate();
        }
    }

    public void moveToNextColumn(Long cardId, List<BoardColumnInfoDTO> columns) {
        try {
            var currentColumnStmt = connection.prepareStatement("SELECT column_id FROM cards WHERE id = ?");
            currentColumnStmt.setLong(1, cardId);
            var rs = currentColumnStmt.executeQuery();

            if (!rs.next()) throw new RuntimeException("Card não encontrado.");
            Long currentColumnId = rs.getLong("column_id");

            int currentOrder = columns.stream()
                    .filter(c -> c.getId().equals(currentColumnId))
                    .findFirst().orElseThrow().getOrder();

            var nextColumn = columns.stream()
                    .filter(c -> c.getOrder() == currentOrder + 1)
                    .findFirst().orElseThrow(() -> new RuntimeException("Não há próxima coluna."));

            var update = connection.prepareStatement("UPDATE cards SET column_id = ? WHERE id = ?");
            update.setLong(1, nextColumn.getId());
            update.setLong(2, cardId);
            update.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao mover card: " + e.getMessage());
        }
    }

    public void block(Long cardId, String reason, List<BoardColumnInfoDTO> columns) {
        try {
            var stmt = connection.prepareStatement("UPDATE cards SET blocked = 1, block_reason = ?, blocks_amount = blocks_amount + 1 WHERE id = ?");
            stmt.setString(1, reason);
            stmt.setLong(2, cardId);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao bloquear o card.");
        }
    }

    public void unblock(Long cardId, String reason) {
        try {
            var stmt = connection.prepareStatement("UPDATE cards SET blocked = 0, block_reason = '' WHERE id = ?");
            stmt.setLong(1, cardId);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao desbloquear o card.");
        }
    }

    public void cancel(Long cardId, Long cancelColumnId, List<BoardColumnInfoDTO> columns) {
        try {
            var stmt = connection.prepareStatement("UPDATE cards SET column_id = ? WHERE id = ?");
            stmt.setLong(1, cancelColumnId);
            stmt.setLong(2, cardId);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cancelar o card.");
        }
    }
}