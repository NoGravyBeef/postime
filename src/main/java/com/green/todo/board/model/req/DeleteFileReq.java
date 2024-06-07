package com.green.todo.board.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class DeleteFileReq {
    @Schema(example = "1", description = "파일의 id")
    private Long boardId;
    @Schema(example = "1", description = "캘린더의 id")
    private Long calendarId;
    @Schema(example = "1", description = "파일의 id")
    private Long fileId;
    @Schema(example = "77e0e5e6-5731-4957-91d8-6efbd15b9a8e.jpg", description = "파일의 이름")
    private String fileName;
}
