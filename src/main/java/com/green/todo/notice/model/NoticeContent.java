package com.green.todo.notice.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.text.StringEscapeUtils;  //백슬래시를 HTML 엔티티로 변환

@Getter
@Setter
public class NoticeContent {
    private String newUserName;
    private String calendarName;
    private String boardName;
    private String content;

    public String getNewMemToOne() {
        return String.format("'%s' 캘린더가 추가되었습니다.", calendarName);
    }

    public String getNewMemToOther() {
        return String.format("'%s'님이 '%s'에 추가되었습니다.", newUserName, calendarName);
    }

    public String getNewBoard() {
        return String.format("'%s'에 새로운 일정이 등록되었습니다. : '%s'", calendarName, content);
    }

    public String getNewComment() {
        return String.format("'%s'에 새로운 댓글이 있습니다. : '%s'", boardName, content);
    }

}
