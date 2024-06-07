package com.green.todo.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PwdAcRes {
    @Schema(example = "3", description = "유저 PK")
    private long userId;
    @Schema(example = "gENrL1vg",description = "코드는 8자리로 구성되어있음")
    private String code;
}
