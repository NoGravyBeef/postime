package com.green.todo.notice.model.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeGetRes {
    @Schema(example = "3", description = "알림 PK")
    private Long noticeId;
    @Schema(example = "5", description = "알림받은 캘린더의 PK")
    private Long calendarId;
    @Schema(example = "새로운 댓글이 추가되었습니다.", description = "알림 내용")
    private String content;
    @Schema(example = "2024-06-03 15:50:13", description = "생성 일시")
    private String createdAt;
    @Schema(example = "2024-06-03 15:50:13", description = "수정 일시")
    private String updatedAt;
}
