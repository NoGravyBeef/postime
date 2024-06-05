package com.green.todo.notice.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticePostReq {
    @JsonIgnore
    private long noticeId;
    @Schema(example = "3", description = "알림을 받은 캘린더의 PK")
    private Long calendarId;
    @Schema(example = "새로운 댓글이 추가되었습니다.", description = "알림 내용")
    private String content;
}
