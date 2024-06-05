package com.green.todo.tag.model.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardTagDeleteReq {
    private long boardId;
    private long tagId;
}
