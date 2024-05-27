package com.green.todo.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSignInRes {
    private long userIndex;
    private String userName;
}
