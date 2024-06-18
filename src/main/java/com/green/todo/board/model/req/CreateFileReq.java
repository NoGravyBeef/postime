package com.green.todo.board.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateFileReq {
    @Schema(example = "1", description = "보드의 id")
    private Long boardId;
    @Schema(example = "1", description = "캘린더의 id")
    private Long calendarId;

}
