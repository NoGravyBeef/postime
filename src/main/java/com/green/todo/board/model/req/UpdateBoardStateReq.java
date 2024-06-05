package com.green.todo.board.model.req;

import lombok.Data;

@Data
public class UpdateBoardStateReq {
    private Long boardId;
    private Integer state;
}
