package com.green.todo.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindIdReq {
    @Schema(example = "백창현",description = "유저 이름")
    private  String name;
    @Schema(example = "hjajaja@hanmail.net",description = "이메일 형식에 맞게 작성하여 주십시오")
    private  String email;

}
