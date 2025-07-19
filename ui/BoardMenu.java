package br.com.dio.board.ui;

import br.com.dio.board.config.ConnectionConfig;
import br.com.dio.board.dto.BoardColumnInfoDTO;
import br.com.dio.board.entity.BoardEntity;
import br.com.dio.board.entity.CardEntity;
import br.com.dio.board.service.BoardColumnQueryService;
import br.com.dio.board.service.BoardQueryService;
import br.com.dio.board.service.CardQueryService;
import br.com.dio.board.service.CardService;

import java.io.PrintStream;
import java.sql.SQLException;
import java.util.Scanner;

public class BoardMenu {

    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");
    private final BoardEntity entity;

    public BoardMenu(BoardEntity entity) {
        this.entity = entity;
    }

    // ...existing code...
    private static void accept(Object card) {
        CardEntity c = (CardEntity) card;
        System.out.printf("- Card %d: %s%n", c.getId(), c.getTitle());
    }

    public void execute() {
        try {
            int option = -1;
            while (option != 9) {
                System.out.printf("\nGerenciando Board [%s - ID: %d]\n", entity.getName(), entity.getId());
                System.out.println("1 - Criar card");
                System.out.println("2 - Mover card");
                System.out.println("3 - Bloquear card");
                System.out.println("4 - Desbloquear card");
                System.out.println("5 - Cancelar card");
                System.out.println("6 - Ver board");
                System.out.println("7 - Ver coluna");
                System.out.println("8 - Ver card");
                System.out.println("9 - Voltar");

                option = scanner.nextInt();

                switch (option) {
                    case 1 -> createCard();
                    case 2 -> moveCardToNextColumn();
                    case 3 -> blockCard();
                    case 4 -> unblockCard();
                    case 5 -> cancelCard();
                    case 6 -> showBoard();
                    case 7 -> showColumn();
                    case 8 -> showCard();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createCard() throws SQLException {
        var card = new CardEntity();
        System.out.println("Título do card:");
        card.setTitle(scanner.next());
        System.out.println("Descrição do card:");
        card.setDescription(scanner.next());
        card.setBoardColumn(entity.getInitialColumn());

        try (var conn = ConnectionConfig.getConnection()) {
            new CardService(conn).create(card);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void moveCardToNextColumn() throws SQLException {
        System.out.println("ID do card a mover:");
        long id = scanner.nextLong();

        var infoList = entity.getBoardColumns().stream()
                .map(c -> new BoardColumnInfoDTO(c.getId(), c.getOrder(), c.getKind()))
                .toList();

        try (var conn = ConnectionConfig.getConnection()) {
            new CardService(conn).moveToNextColumn(id, infoList);
        }
    }

    private void blockCard() throws SQLException {
        System.out.println("ID do card:");
        long id = scanner.nextLong();
        System.out.println("Motivo do bloqueio:");
        String reason = scanner.next();

        var infoList = entity.getBoardColumns().stream()
                .map(c -> new BoardColumnInfoDTO(c.getId(), c.getOrder(), c.getKind()))
                .toList();

        try (var conn = ConnectionConfig.getConnection()) {
            new CardService(conn).block(id, reason, infoList);
        }
    }

    private void unblockCard() throws SQLException {
        System.out.println("ID do card:");
        long id = scanner.nextLong();

        try (var conn = ConnectionConfig.getConnection()) {
            new CardService(conn).unblock(id, "");
        }
    }

    private void cancelCard() throws SQLException {
        System.out.println("ID do card:");
        long id = scanner.nextLong();

        var cancelCol = entity.getCancelColumn();
        var infoList = entity.getBoardColumns().stream()
                .map(c -> new BoardColumnInfoDTO(c.getId(), c.getOrder(), c.getKind()))
                .toList();

        try (var conn = ConnectionConfig.getConnection()) {
            new CardService(conn).cancel(id, cancelCol.getId(), infoList);
        }
    }

    private void showBoard() throws SQLException {
        try (var conn = ConnectionConfig.getConnection()) {
            var board = new BoardQueryService(conn).showBoardDetails(entity.getId());
            board.ifPresent(b -> {
                System.out.printf("Board [%s]\n", b.getName());
                b.getBoardColumns().forEach(c -> System.out.printf("- Coluna %s (%s)\n", c.getName(), c.getKind()));
            });
        }
    }

    private void showColumn() throws SQLException {
        System.out.println("Digite o ID da coluna:");
        long id = scanner.nextLong();

        try (var conn = ConnectionConfig.getConnection()) {
            var column = new BoardColumnQueryService(conn).findById(id);
            column.ifPresent(c -> {
                System.out.printf("Coluna: %s (%s)\n", c.getName(), c.getKind());
                c.getCards().forEach(BoardMenu::accept);
            });
        }
    }

    private void showCard() throws SQLException {
        System.out.println("ID do card:");
        long id = scanner.nextLong();

        try (var conn = ConnectionConfig.getConnection()) {
            var card = new CardQueryService(conn).findById(id);
            card.ifPresent(c -> {
                System.out.printf("Card %s\n", c.getTitle());
                System.out.printf("Descrição: %s\n", c.getDescription());
                System.out.printf("Bloqueado? %s\n", c.isBlocked());
                System.out.printf("Motivo: %s\n", c.getBlockReason());
                System.out.printf("Coluna atual: %s\n", c.getBoardColumnName());
            });
        }
    }
}