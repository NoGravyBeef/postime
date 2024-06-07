package com.green.todo.tag.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardTagDeleteReq {
    @Schema(example = "2", description = "삭제할 태그가 있는 보드의 PK")
    private long boardId;
    @Schema(example = "1", description = "삭제할 태그의 PK")
    private long tagId;
}
