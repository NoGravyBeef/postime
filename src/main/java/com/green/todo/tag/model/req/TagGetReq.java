package com.green.todo.tag.model.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TagGetReq {
    private String title;
    private long calendarId;
}
