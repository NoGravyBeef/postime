package com.green.todo.notice.model.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeUpdateReq {
    @Schema(example = "5", description = "알림 PK")
    private Long noticeId;
    @Schema(example = "7", description = "로그인된 유저 PK")
    private Long signedUserId;
}
