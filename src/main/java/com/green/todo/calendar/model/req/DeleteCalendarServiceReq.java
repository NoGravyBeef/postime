package com.green.todo.calendar.model.req;

import lombok.Data;

@Data
public class DeleteCalendarServiceReq {
    private Long signedUserId;
    private Long calendarId;
}
