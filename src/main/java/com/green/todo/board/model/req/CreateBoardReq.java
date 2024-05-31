package com.green.todo.board.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CreateBoardReq {
    @JsonIgnore
    private long boardId;
    private long signedUserId;
    private String title;
    private String content;
    private int color;
    private int state;
    private String startDay;
    private String dDay;
    private String deadLine;
}
