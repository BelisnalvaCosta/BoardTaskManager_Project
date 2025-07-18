package br.com.dio.board.service;

import br.com.dio.board.entity.BoardColumnEntity;
import br.com.dio.board.entity.BoardColumnKindEnum;
import br.com.dio.board.entity.CardEntity;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BoardColumnQueryService {

    private final Connection connection;

    public BoardColumnQueryService(Connection connection) {
        this.connection = connection;
    }

    public Optional<BoardColumnEntity> findById(Long id) {
        try {
            var stmt = connection.prepareStatement("SELECT * FROM board_columns WHERE id = ?");
            stmt.setLong(1, id);
            var rs = stmt.executeQuery();

            if (!rs.next()) return Optional.empty();

            var column = new BoardColumnEntity();
            column.setId(rs.getLong("id"));
            column.setName(rs.getString("name"));
            column.setKind(BoardColumnKindEnum.valueOf(rs.getString("kind")));
            column.setOrder(rs.getInt("order"));

            column.setBoardId(rs.getLong("board_id"));

            column.setBoardId(rs.getLong("board_id"));

            // Preencher cards
            var cardsStmt = connection.prepareStatement("SELECT * FROM cards WHERE column_id = ?");
            cardsStmt.setLong(1, id);
            var cardsRS = cardsStmt.executeQuery();

            List<CardEntity> cards = new ArrayList<>();
            while (cardsRS.next()) {
                var card = new CardEntity();
                card.setId(cardsRS.getLong("id"));
                card.setTitle(cardsRS.getString("title"));
                card.setDescription(cardsRS.getString("description"));
                cards.add(card);
            }

            column.setCards(cards);

            return Optional.of(column);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<BoardColumnEntity> findByBoardId(Long boardId) {
        try {
            var stmt = connection.prepareStatement("SELECT * FROM board_columns WHERE board_id = ? ORDER BY \"order\"");
            stmt.setLong(1, boardId);
            var rs = stmt.executeQuery();

            List<BoardColumnEntity> columns = new ArrayList<>();

            while (rs.next()) {
                var col = new BoardColumnEntity();
                col.setId(rs.getLong("id"));
                col.setName(rs.getString("name"));
                col.setKind(BoardColumnKindEnum.valueOf(rs.getString("kind")));
                col.setOrder(rs.getInt("order"));
                col.setBoardId(boardId);
                columns.add(col);
            }

            return columns;
        } catch (Exception e) {
            return List.of();
        }
    }
}