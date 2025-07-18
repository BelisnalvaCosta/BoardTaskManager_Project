package br.com.dio.board.dto;

import br.com.dio.board.entity.BoardColumnKindEnum;

public class BoardColumnInfoDTO {
    private Long id;
    private int order;
    private BoardColumnKindEnum kind;

    public BoardColumnInfoDTO(Long id, int order, BoardColumnKindEnum kind) {
        this.id = id;
        this.order = order;
        this.kind = kind;
    }

    public Long getId() {
        return id;
    }

    public int getOrder() {
        return order;
    }

    public BoardColumnKindEnum getKind() {
        return kind;
    }
}