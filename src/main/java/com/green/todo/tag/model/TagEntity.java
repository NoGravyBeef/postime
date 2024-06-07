package com.green.todo.tag.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagEntity {
    @Schema(example = "1", description = "태그의 id")
    private long tagId;
    @Schema(example = "2", description = "캘린더의 id")
    private long calendarId;
    @Schema(example = "title", description = "태그 이름")
    private String title;
    @Schema(example = "2", description = "태그의 색상")
    private int color;
    @Schema(example = "2024-06-05 18:09:47", description = "태그 생성일")
    private String createdAt;
    @Schema(example = "2024-07-15 18:09:47", description = "태그 수정일")
    private String updatedAt;
}
