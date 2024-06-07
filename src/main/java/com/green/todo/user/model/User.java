package com.green.todo.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private long userId;
    private String id;
    private String email;
    private String pwd;
    private String name;
    private String createdAt;
    private String updatedAt;
}
