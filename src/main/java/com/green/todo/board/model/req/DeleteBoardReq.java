package com.green.todo.board.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class DeleteBoardReq {
    @Schema(example = "1", description = "파일의 id")
    private Long boardId;
    @Schema(example = "1", description = "캘린더의 id")
    private Long calendarId;
}
