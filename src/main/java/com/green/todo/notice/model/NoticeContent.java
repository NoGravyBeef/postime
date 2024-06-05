package com.green.todo.notice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeContent {
    private String newUserName;
    private String calendarName;
    private String boardName;

    public String getNewMemToOne() {
        return String.format("'%s' 캘린더가 추가되었습니다.", calendarName);
    }
    public String getNewMemToOther() {
        return String.format("'%s'님이 '%s'에 추가되었습니다.", newUserName, calendarName);
    }
    public String getNewBoard() {
        return String.format("'%s'에 새로운 일정이 등록되었습니다.", calendarName);
    }
    public String getNewComment() {
        return String.format("'%s'에 새로운 댓글이 있습니다.", boardName);
    }
}
