package com.green.todo.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PwdAcReq {
    @Schema(example = "dwdwdkg@gmail.com",description = "이메일 형식에 맞게 작성하여 주십시오")
    private String email;
    @Schema(example = "qorckus183",description = "아이디는 영문자와 숫자로만 구성되어야 하며, 길이는 6자 이상 12자 이하")
    private String id;
}
