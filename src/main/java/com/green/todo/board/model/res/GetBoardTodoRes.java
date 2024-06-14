package com.green.todo.board.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.green.todo.tag.model.TagEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class GetBoardTodoRes {
    @Schema(example = "1", description = "보드 id")
    private Long boardId;
    @Schema(example = "1", description = "캘린더 id")
    private Long calendarId;
    @Schema(example = "1", description = "캘린더 색깔")
    private String color;
    @Schema(example = "title", description = "글 제목")
    private String title;
    @Schema(example = "2024-04-12", description = "일정 마감일")
    @JsonProperty("dDay")
    private String dDay;
    private List<TagEntity> tags;
}
