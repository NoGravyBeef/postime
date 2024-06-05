package com.green.todo.calendar.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PlusCalendarUserReq {
    @Schema(example = "캘린더 id 입력해야함.(int 타입)")
    private Long calendarId;
    @Schema(example = "유저 email 입력해야함.")
    private String userEmail;

}
