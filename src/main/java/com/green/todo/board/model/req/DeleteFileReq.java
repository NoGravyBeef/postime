package com.green.todo.board.model.req;

import lombok.Data;

@Data
public class DeleteFileReq {
    private Long calendarId;
    private Long boardId;
    private Long fileId;
    private String fileName;
}
