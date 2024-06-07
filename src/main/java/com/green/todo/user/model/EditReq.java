package com.green.todo.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditReq {
    @Schema(example = "3", description = "유저 PK")
    private Long userId;
    @Schema(example = "faekf@naver.com",description = "이메일 형식에 맞게 작성하여 주십시오")
    private String email;
    @Schema(example = "백창현",description = "유저 이름")
    private String name;
    @Schema(example = "Ckdgusdl!!@",description = "새 비번은 대소문자,숫자,특문을 포함하여 8자 이상 20자 이하 ")
    private String newPw;
    @Schema(example = "Rlaalswlqkqh!!#",description = "비번은 대소문자,숫자,특문을 포함하여 8자 이상 20자 이하 ")
    private String pwd;
}
