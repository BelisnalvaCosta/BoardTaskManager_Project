package br.com.dio.board.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.dio.board.InitDatabase;
import br.com.dio.board.config.ConnectionConfig;
import br.com.dio.board.entity.BoardColumnEntity;
import br.com.dio.board.entity.BoardColumnKindEnum;
import br.com.dio.board.entity.BoardEntity;
import br.com.dio.board.service.BoardQueryService;
import br.com.dio.board.service.BoardService;

public class MainMenu {

    private final Scanner scanner = new Scanner(System.in).useDelimiter("\n");

    // ...existing code...
    public static void main(String[] args) throws SQLException {
        InitDatabase.initialize();
        new MainMenu().execute();
    }

    public void execute() throws SQLException {
        System.out.println("Bem-vindo ao Gerenciador de Boards!");
        int option = -1;
        while (option != 4) {
            System.out.println("\n1 - Criar novo board");
            System.out.println("2 - Selecionar board existente");
            System.out.println("3 - Excluir board");
            System.out.println("4 - Sair");
            option = scanner.nextInt();

            switch (option) {
                case 1 -> createBoard();
                case 2 -> selectBoard();
                case 3 -> deleteBoard();
                case 4 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void createBoard() throws SQLException {
        var entity = new BoardEntity();
        System.out.println("Informe o nome do board:");
        entity.setName(scanner.next());

        System.out.println("Quantas colunas adicionais (entre Inicial e Final)?");
        int extra = scanner.nextInt();

        List<BoardColumnEntity> columns = new ArrayList<>();

        System.out.println("Nome da Coluna Inicial:");
        columns.add(createColumn(scanner.next(), BoardColumnKindEnum.INITIAL, 0));

        for (int i = 0; i < extra; i++) {
            System.out.println("Nome da Coluna Intermediária (PENDING):");
            columns.add(createColumn(scanner.next(), BoardColumnKindEnum.PENDING, i + 1));
        }

        System.out.println("Nome da Coluna Final:");
        columns.add(createColumn(scanner.next(), BoardColumnKindEnum.FINAL, extra + 1));

        System.out.println("Nome da Coluna de Cancelamento:");
        columns.add(createColumn(scanner.next(), BoardColumnKindEnum.CANCEL, extra + 2));

        entity.setBoardColumns(columns);

        try (var conn = ConnectionConfig.getConnection()) {
            new BoardService(conn).insert(entity);
        }
    }

    private BoardColumnEntity createColumn(String name, BoardColumnKindEnum kind, int order) {
        var col = new BoardColumnEntity();
        col.setName(name);
        col.setKind(kind);
        col.setOrder(order);
        return col;
    }

    private void selectBoard() throws SQLException {
        System.out.println("Informe o ID do board:");
        long id = scanner.nextLong();

        try (var conn = ConnectionConfig.getConnection()) {
            var optional = new BoardQueryService(conn).findById(id);
            optional.ifPresentOrElse(
                    board -> new BoardMenu(board).execute(),
                    () -> System.out.println("Board não encontrado.")
            );
        }
    }

    private void deleteBoard() throws SQLException {
        System.out.println("Informe o ID do board a excluir:");
        long id = scanner.nextLong();

        try (var conn = ConnectionConfig.getConnection()) {
            boolean deleted = new BoardService(conn).delete(id);
            if (deleted) {
                System.out.println("Board excluído com sucesso.");
            } else {
                System.out.println("Board não encontrado.");
            }
        }
    }
}