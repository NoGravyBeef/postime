package com.green.todo.board.model.res;

import lombok.Data;

@Data
public class GetViewCalendarRes {
    private Long boardId;
    private Long calendarId;
    private String title;
    private String start;
    private String end;
    private String backgroundColor;
}
