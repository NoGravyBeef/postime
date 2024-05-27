package com.green.todo.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserSignUpReq {
    @Schema(example = "아이디 입력")
    private String userId;
    @Schema(example = "비밀번호 입력")
    private String userPw;
    @Schema(example = "이름 입력")
    private String userName;
}
