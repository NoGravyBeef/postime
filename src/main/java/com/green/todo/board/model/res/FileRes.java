package com.green.todo.board.model.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileRes {
    private Long fileId;
    private String fileName;
}
