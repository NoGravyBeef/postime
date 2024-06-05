package com.green.todo.board.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.todo.tag.model.req.TagPostReq;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateBoardReq {
    @JsonIgnore
    private long boardId;
    private Long calendarId;
    private Long signedUserId;
    private String title;
    private String content;
//    private Integer state;
    private String startDay;
    private String dDay;
    private String deadLine;
    private List<Long> existTag = new ArrayList<>();
    private List<TagPostReq> notExistTag = new ArrayList<>();
}
