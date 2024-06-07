package com.green.todo.calendar.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateCalendarReq {
    @JsonIgnore
    private Long calendarId;
    @Schema(example = "1", description = "로그인 중인 user_id 보내주기")
    private Long signedUserId;
    @Schema(example = "title", description = "캘린더 이름 입력")
    private String title;
    @Schema(example = "3", description = "1~9사이의 색상 값 늫으세요~!")
    private Integer color;
}
