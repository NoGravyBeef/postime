package com.green.todo.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInPostReq {
    @Schema(example = "qorckus183",description = "아이디는 영문자와 숫자로만 구성되어야 하며, 길이는 6자 이상 12자 이하")
    private String id;
    @Schema(example = "Ckdgusdl@@!",description = "비번은 대소문자,숫자,특문을 포함하여 8자 이상 20자 이하 ")
    private String pwd;

}
