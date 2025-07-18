package br.com.dio.board.entity;
import java.util.List;

public class BoardEntity {
    private Long id;
    private String name;
    private List<BoardColumnEntity> boardColumns;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<BoardColumnEntity> getBoardColumns() { return boardColumns; }
    public void setBoardColumns(List<BoardColumnEntity> boardColumns) {
        this.boardColumns = boardColumns;
    }

    public BoardColumnEntity getInitialColumn() {
        return boardColumns.stream()
                .filter(c -> c.getKind() == BoardColumnKindEnum.INITIAL)
                .findFirst()
                .orElse(null);
    }

    public BoardColumnEntity getCancelColumn() {
        return boardColumns.stream()
                .filter(c -> c.getKind() == BoardColumnKindEnum.CANCEL)
                .findFirst()
                .orElse(null);
    }
}