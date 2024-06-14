package com.green.todo.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckPassword {
    @Schema(example = "Ckdgusdlqkqh@@3", description = "유저의 현재 비밀번호")
    private String pwd;
    @Schema(example = "3", description = "유저 PK")
    private long userId;
}
