package com.green.todo.calendar;

import com.green.todo.calendar.model.req.DeleteCalendarServiceReq;
import com.green.todo.calendar.model.req.CreateCalendarReq;
import com.green.todo.calendar.model.req.DeleteCalendarMemberReq;
import com.green.todo.calendar.model.req.UpdateCalendarReq;
import com.green.todo.calendar.model.res.GetCalendarRes;
import com.green.todo.calendar.model.res.GetUserByEmailRes;
import com.green.todo.calendar.model.res.MemRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CalendarMapper {
    long createCalendar(CreateCalendarReq p);
    GetUserByEmailRes getUserIdByEmail(String email);
    int plusCalendarUserById(@Param("calendarId") long calendarId, @Param("signedUserId") long signedUserId);
    List<GetCalendarRes> getCalendarList(long signedUserId);
    int updateCalendar(UpdateCalendarReq p);
    int deleteCalendar(Long signedUserId, Long calendarId);
    void deleteCalendarPermanent(long calendarId);
    List<MemRes> getMemberList(Long calendarId, Long signedUserIdLong);
    int deleteCalendarMember(DeleteCalendarMemberReq p);
    long selecteCalendar(long calendarId);
}
