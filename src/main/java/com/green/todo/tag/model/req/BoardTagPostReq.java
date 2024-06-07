package com.green.todo.tag.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class BoardTagPostReq {
    @Schema(example = "1", description = "태그가 달리는 보드의 PK")
    private long boardId;
    @Schema(example = "[1, 2]", description = "태그 PK 들")
    private List<Long> tags;
}
