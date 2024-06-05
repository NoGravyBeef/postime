package com.green.todo.comment.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentUpdateReq {
    @Schema(example = "댓글 내용", description = "New 댓글")
    private String content;
    @Schema(example = "8", description = "수정할 댓글 PK")
    private Long commentId;
    @Schema(example = "5", description = "댓글 작성자 PK")
    private Long signedUserId;
}
