package com.green.todo.notice.model.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeDelReq {
    private long userId;
    private long noticeId;
}
