package com.green.todo.board.model.res;

import com.green.todo.tag.model.TagEntity;
import lombok.Data;

import java.util.List;

@Data
public class GetBoardRes {
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
    private List<FileRes> files;
    private List<TagEntity> tags;
}
