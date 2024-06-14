package com.green.todo.board.model.res;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IdDto {
    private Long calendarId;
    private Long boardId;
}
