package com.green.todo.tag.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagEntity {
    private long tagId;
    private long calendarId;
    private String title;
    private int color;
    private String createdAt;
    private String updatedAt;
}
