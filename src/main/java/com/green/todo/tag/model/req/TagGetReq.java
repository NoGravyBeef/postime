package com.green.todo.tag.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TagGetReq {
    @Schema(example = "태그1", description = "태그 이름")
    private String title;
    @Schema(example = "2", description = "태그가 있는 캘린더의 PK")
    private long calendarId;
}
