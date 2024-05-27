package com.green.todo.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserSignInReq {
    @Schema(example = "아이디 입력")
    private String userId;
    @Schema(example = "비밀번호 입력")
    private String userPw;
}
