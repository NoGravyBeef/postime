package com.green.todo.board.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.green.todo.tag.model.TagEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class GetBoardRes {
    @Schema(example = "1", description = "보드 id")
    private Long boardId;
    @Schema(example = "1", description = "캘린더 id")
    private Long calendarId;
    @Schema(example = "1", description = "유저 id")
    private Long userId;
    @Schema(example = "title", description = "글 제목")
    private String title;
    @Schema(example = "content", description = "글 내용")
    private String content;
    @Schema(example = "2", description = "현재 보드 상태")
    private Integer state;
    @Schema(example = "2024-04-11", description = "보드(일정) 시작일")
    private String startDay;
    @Schema(example = "2024-04-12", description = "보드(일정) 마감일")
    @JsonProperty("dDay")
    private String dDay;
    @Schema(example = "12:30:00", description = "보드(일정) 마감 시간")
    private String deadLine;
    @Schema(example = "2024-06-05 18:09:47", description = "보드(일정) 생성일")
    private String createdAt;
    @Schema(example = "2024-07-15 18:09:47", description = "보드(일정) 수정일")
    private String updatedAt;
    @Schema(description = "보드에 넣은 파일들")
    private List<FileRes> files;
    @Schema(description = "보드에 넣은 태그들")
    private List<TagEntity> tags;
}
