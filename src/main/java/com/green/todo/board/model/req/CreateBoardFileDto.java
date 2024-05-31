package com.green.todo.board.model.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBoardFileDto {
    private long boardId;
    @Builder.Default
    private List<String> fileNames = new ArrayList<>();
}




