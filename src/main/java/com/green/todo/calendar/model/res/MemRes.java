package com.green.todo.calendar.model.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MemRes {
    @Schema(example = "3")
    private long userId;
    @Schema(example = "이민엽")
    private String name;
    @Schema(example = "asdf@gmail.com")
    private String email;
}
