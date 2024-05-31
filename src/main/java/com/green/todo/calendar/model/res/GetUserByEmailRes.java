package com.green.todo.calendar.model.res;

import lombok.Data;

@Data
public class GetUserByEmailRes {
    private long userId;
    private String name;
}
