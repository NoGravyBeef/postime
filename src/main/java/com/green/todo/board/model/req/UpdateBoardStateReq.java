package com.green.todo.board.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class UpdateBoardStateReq {
    @JsonIgnore
    private Long resultBoardId;
    @Schema(example = "1", description = "파일의 id")
    private Long boardId;
    @Schema(example = "1", description = "보드 상태")
    private Integer state;
}
