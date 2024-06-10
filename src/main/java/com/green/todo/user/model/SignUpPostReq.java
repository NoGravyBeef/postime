package com.green.todo.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpPostReq {
    @JsonIgnore
    private long userId;

    @Schema(example = "qorckus183", description = "유저 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;
    @Schema(example = "Ckdgusdlqkqh@@3", description = "유저 비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
    private String pwd;
    @Schema(example = "백창현", description = "유저 이름")
    private String name;
    @Schema(example = "asdqwe@naver.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
}
