package com.green.todo.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignInRes {
    @Schema(example = "3", description = "유저 PK")
    private long userId;
    @Schema(example = "백창현",description = "유저 이름")
    private String name;
    @Schema(example = "dwdwdkg@gmail.com",description = "이메일 형식에 맞게 작성하여 주십시오")
    private String email;

}
