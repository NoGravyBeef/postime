package com.green.todo.board.model.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileCreateDto {
    private Long fileId;
    private Long boardId;
    private String newName;
}
