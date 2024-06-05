package com.green.todo.board.model.req;

import lombok.Data;

import java.util.List;

@Data
public class UpdateBoardReq {
    private Long boardId;
    private String title;
    private String content;
    private String startDay;
    private String dDay;
    private String deadLine;
    private List<Long> deltagList;
}
