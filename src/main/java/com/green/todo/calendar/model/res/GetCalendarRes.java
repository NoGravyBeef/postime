package com.green.todo.calendar.model.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GetCalendarRes {
    @Schema(example = "1")
    private long calendarId;
    @Schema(example = "캘린더 이름")
    private String title;
    @Schema(example = "3")
    private int color;
    @Schema(example = "2022-05-30 18:14:38")
    private String createdAt;
    @Schema(example = "2022-05-31 13:09:02")
    private String updatedAt;
}
