package com.green.todo.board.model.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class UpdateBoardReq {
    @Schema(example = "1", description = "보드 id")
    private Long boardId;
    @Schema(example = "1", description = "캘린더 id")
    private Long calendarId;
    @Schema(example = "title", description = "보드 제목")
    private String title;
    @Schema(example = "content", description = "보드 본문")
    private String content;
    @Schema(example = "2024-04-11", description = "시작일")
    private String startDay;
    @Schema(example = "2024-04-12", description = "마감일")
    @JsonProperty("dDay")
    private String dDay;
    @Schema(example = "12:30:00", description = "마감 시간")
    private String deadLine;
    @Schema(example = "[1, 2]")
    private List<Long> deltagList;
}
