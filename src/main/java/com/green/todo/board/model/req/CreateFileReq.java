package com.green.todo.board.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.util.pattern.PathPattern;

@Data
public class CreateFileReq {
    @JsonIgnore
    private Long fileId;
    private Long boardId;
    private Long calendarId;
    @JsonIgnore
    private String fileName;
}
