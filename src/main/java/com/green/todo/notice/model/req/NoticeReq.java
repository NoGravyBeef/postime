package com.green.todo.notice.model.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NoticeReq {
    private long calendarId;
    private long userId;
}
