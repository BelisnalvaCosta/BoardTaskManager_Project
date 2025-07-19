package br.com.dio.board.entity;

public class CardEntity {
    private Long id;
    private String title;
    private String description;
    private BoardColumnEntity boardColumn;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BoardColumnEntity getBoardColumn() { return boardColumn; }
    public void setBoardColumn(BoardColumnEntity boardColumn) {
        this.boardColumn = boardColumn;
    }

    public void setBlocked(boolean blocked) {
    }

    public void setBlockReason(String blockReason) {
    }

    public void setBlocksAmount(int blocksAmount) {
    }

    public void setColumnId(long columnId) {
    }

    public void setBoardColumnName(String name) {
    }

    public long getColumnId() {
        return id;
    }

    public Object getBlockReason() {
        return null;
    }

    public Object getBoardColumnName() {
        return null;
    }

    public Object isBlocked() {
        return null;
    }
}