package com.green.todo.board.model.req;

import lombok.Data;

@Data
public class UpdateBoardDnDReq {
    private Long boardId;
    private String startDay;
    private String dDay;
}
