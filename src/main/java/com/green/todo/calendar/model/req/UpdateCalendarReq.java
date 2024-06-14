package com.green.todo.calendar.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateCalendarReq {
    @Schema(example = "선택한 calendar_id 넣으세용~!~!")
    private Long calendarId;
    @Schema(example = "수정할 캘린더 이름 넣으세요~!~!")
    private String title;
    @Schema(example = "수정할 캘린더 색상 넣으세요~!~!")
    private String color;
}
