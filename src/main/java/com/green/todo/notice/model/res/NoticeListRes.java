package com.green.todo.notice.model.res;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class NoticeListRes {
    private List<NoticeGetRes> notice;
    private int notRead;
}
