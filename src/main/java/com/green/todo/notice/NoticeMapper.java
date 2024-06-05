package com.green.todo.notice;

import com.green.todo.notice.model.req.NoticeListPostReq;
import com.green.todo.notice.model.req.NoticePostReq;
import com.green.todo.notice.model.req.NoticeUpdateReq;
import com.green.todo.notice.model.res.NoticeGetRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {
    int insertNotice(NoticePostReq p);
    int insertNoticeList(NoticeListPostReq p);

    List<Long> getCalendarMember(long calendarId);

    String getCalendarName(long calendarId);
    String getUserName(long userId);
    String getBoardName(long boardId);

    List<NoticeGetRes> getNoticeList (long signedUserId);
    int notRead(long signedUserId);

    int updateNotice(NoticeUpdateReq p);
}
