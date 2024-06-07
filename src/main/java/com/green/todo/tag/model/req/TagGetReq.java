package com.green.todo.tag.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TagGetReq {
    @Schema(example = "")
    private String title;
    private long calendarId;
}
