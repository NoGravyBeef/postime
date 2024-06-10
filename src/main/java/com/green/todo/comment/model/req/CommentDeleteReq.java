package com.green.todo.comment.model.req;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;

@Getter
@Setter
public class CommentDeleteReq {
    @Parameter(name = "comment_id")
    @Schema(example = "3", description = "삭제할 댓글의 PK")
    private String commentId;
    @Parameter(name = "signed_user_id")
    @Schema(example = "5", description = "로그인된 유저의 PK (선택된 댓글의 작성자)")
    private String signedUserId;

    @ConstructorProperties({"comment_id","signed_user_id"})
    public CommentDeleteReq(String commentId, String signedUserId) {
        this.commentId = commentId;
        this.signedUserId = signedUserId;
    }
}
