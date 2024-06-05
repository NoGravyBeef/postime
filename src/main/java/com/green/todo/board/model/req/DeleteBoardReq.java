package com.green.todo.board.model.req;

import lombok.Data;


@Data
public class DeleteBoardReq {
    private Long boardId;
    private Long calendarId;
}
