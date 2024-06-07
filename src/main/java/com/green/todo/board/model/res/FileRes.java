package com.green.todo.board.model.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileRes {
    @Schema(example = "1", description = "파일의 id")
    private Long fileId;
    @Schema(example = "77e0e5e6-5731-4957-91d8-6efbd15b9a8e.jpg", description = "파일의 이름")
    private String fileName;
}
