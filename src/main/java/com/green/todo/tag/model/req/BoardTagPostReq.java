package com.green.todo.tag.model.req;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class BoardTagPostReq {
    private long boardId;
    private List<Long> tags;
}
