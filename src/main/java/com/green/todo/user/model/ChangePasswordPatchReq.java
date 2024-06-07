package com.green.todo.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordPatchReq {
    @Schema(example = "3", description = "유저 PK")
    private long userId;
    @Schema(example = "Ckdgusdlqkqh@@3", description = "새 비번은 대소문자,숫자,특문을 포함하여 8자 이상 20자 이하 ")
    private String newPw;

}
