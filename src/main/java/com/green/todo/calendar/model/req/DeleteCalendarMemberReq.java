package com.green.todo.calendar.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DeleteCalendarMemberReq {
    @Schema(example = "3")
    private Long signedUserId;
    @Schema(example = "2")
    private Long calendarId;
    @Schema(example = "5")
    private Long userId;
}
