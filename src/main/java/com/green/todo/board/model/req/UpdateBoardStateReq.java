package com.green.todo.board.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateBoardStateReq {
    @Schema(example = "1", description = "파일의 id")
    private Long boardId;
    @Schema(example = "1", description = "보드 상태")
    private Integer state;
}
