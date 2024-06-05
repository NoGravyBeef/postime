package com.green.todo.board.model.res;

import lombok.Data;

@Data
public class BoardEntity {
    private Long boardId;
    private Long calendarId;
    private Long userId;
    private String title;
    private String content;
    private Integer state;
    private String startDay;
    private String dDay;
    private String deadLine;
    private String createdAt;
    private String updatedAt;
}
