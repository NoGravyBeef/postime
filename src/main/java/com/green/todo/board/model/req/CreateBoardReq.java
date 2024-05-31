package com.green.todo.board.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CreateBoardReq {
    @JsonIgnore
    private long boardId;
    private Long calendarId;
    private Long signedUserId;
    private String title;
    private String content;
    private Integer state;
    private String startDay;
    private String dDay;
    private String deadLine;
}
