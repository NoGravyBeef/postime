package com.green.todo.comment.model.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentGetRes {
    @Schema(example = "2", description = "댓글 작성자 PK")
    private Long userId;
    @Schema(example = "댓글", description = "댓글 내용")
    private String content;
    @Schema(example = "2024-06-03 15:50:13", description = "작성 일시")
    private String createdAt;
    @Schema(example = "2024-06-03 15:50:13", description = "수정 일시")
    private String updatedAt;
}
