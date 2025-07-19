package br.com.dio.board.entity;

import java.util.List;

public class BoardColumnEntity {
    private Long id;
    private String name;
    private BoardColumnKindEnum kind;
    private int order;

    // getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BoardColumnKindEnum getKind() {
        return kind;
    }

    public void setKind(BoardColumnKindEnum kind) {
        this.kind = kind;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setBoardId(long boardId) {
    }

    public void setCards(List<CardEntity> cards) {
    }

    public Iterable<Object> getCards() {
        return null;
    }
}

/*

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BoardColumnKindEnum getKind() { return kind; }
    public void setKind(BoardColumnKindEnum kind) { this.kind = kind; }

    public int getOrder() { return order; }
    public void setOrder(int order) { this.order = order; }

    public void setCards(List<CardEntity> c1) {
    }

    public void setBoardId(long boardId) {

    }

    public Iterable<Object> getCards() {
        return null;
    }
}

 */