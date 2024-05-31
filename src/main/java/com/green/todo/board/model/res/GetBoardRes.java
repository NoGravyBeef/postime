package com.green.todo.board.model.res;

import lombok.Data;

import java.util.List;

@Data
public class GetBoardRes {
    private long boardId;
    private long userId;
    private String title;
    private String content;
    private int color;
    private int state;
    private String startDay;
    private String dDay;
    private String deadLine;
    private int position;
    private String createdAt;
    private String updatedAt;
    private List<String> files;
}
