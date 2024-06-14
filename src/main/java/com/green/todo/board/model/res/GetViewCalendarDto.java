package com.green.todo.board.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetViewCalendarDto {
    private String title;
    private String start;
    private String end;
    private String backgroundColor;
}
