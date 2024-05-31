package com.green.todo.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ResultDto<T> {
    @Schema(example = "통신코드 ex) 200, 406")
    private HttpStatus statusCode;
    @Schema(example = "어디서 오류났는지 알려드립니다~!")
    private String resultMsg;
    private T resultData;
}
