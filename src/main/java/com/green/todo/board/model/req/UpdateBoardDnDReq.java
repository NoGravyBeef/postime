package com.green.todo.board.model.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateBoardDnDReq {
    @Schema(example = "1", description = "파일의 id")
    private Long boardId;
    @Schema(example = "2024-04-11", description = "일정 시작일")
    private String startDay;
    @Schema(example = "2024-04-12", description = "일정 마감일")
    @JsonProperty("dDay")
    private String dDay;
}
