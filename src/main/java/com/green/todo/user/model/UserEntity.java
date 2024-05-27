package com.green.todo.user.model;

import lombok.Data;

@Data
public class UserEntity {
    private long userIndex;
    private String userId;
    private String userPw;
    private String userName;
    private String createdAt;
}
