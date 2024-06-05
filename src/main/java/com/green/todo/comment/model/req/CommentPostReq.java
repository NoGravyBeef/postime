package com.green.todo.comment.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentPostReq {
    @JsonIgnore
    private Long commentId;
    @Schema(example = "3", description = "댓글이 달릴 보드의 PK")
    private Long boardId;
    @Schema(example = "5", description = "댓글 작성자 PK")
    private Long signedUserId;
    @Schema(example = "댓글 내용", description = "너 나가")
    private String content;
    @Schema(example = "2", description = "캘린더 PK")
    private Long calendarId;
}
