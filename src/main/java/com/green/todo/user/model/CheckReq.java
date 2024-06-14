package com.green.todo.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckReq {
    @Schema(example = "qorckus183", description = "유저 아이디")
    private String id;
    @Schema(example = "asdqwe@naver.com")
    private String email;
}
