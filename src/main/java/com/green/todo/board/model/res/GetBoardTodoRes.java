package com.green.todo.board.model.res;

import com.green.todo.tag.model.TagEntity;
import lombok.Data;

import java.util.List;

@Data
public class GetBoardTodoRes {
    private Long boardId;
    private Long calendarId;
    private Integer color;
    private String title;
    private String dDay;
    private List<TagEntity> tags;
}
