package com.green.todo.notice.model.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NoticeListPostReq {
    private long noticeId;
    private long userId;
}
