package com.green.todo.board.model.res;

import lombok.Data;

@Data
public class GetBoardMiniRes {
    private Long boardId;
    //얘는 달력에서 정렬떄문에
    private Long calendarId;
    private Long color;
    private String title;
    private String startDay;
    private String dDay;
}
