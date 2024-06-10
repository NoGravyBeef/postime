package com.green.todo.calendar.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.beans.ConstructorProperties;

@Data
public class DeleteCalendarReq {
    @Schema(name = "signed_user_id", example = "로그인중인 유저의 id")
    private String signedUserId;
    @Schema(name = "calendar_id", example = "선택한 캘린더의 id")
    private String calendarId;


    @ConstructorProperties({"calendar_id", "signed_user_id"})
    public DeleteCalendarReq(String calendarId, String signedUserId) {
        this.calendarId = calendarId;
        this.signedUserId = signedUserId;
    }
}
