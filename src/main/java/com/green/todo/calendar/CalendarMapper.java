package com.green.todo.calendar;

import com.green.todo.calendar.model.req.CreateCalendarReq;
import com.green.todo.calendar.model.req.UpdateCalendarReq;
import com.green.todo.calendar.model.res.GetCalendarRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CalendarMapper {
    long createCalendar(CreateCalendarReq p);
    Long getUserIdByEmail(String email);
    int plusCalendarUserById(@Param("calendarId") long calendarId, @Param("signedUserId") long signedUserId);
    List<GetCalendarRes> getCalendarList(long signedUserId);
    int updateCalendar(UpdateCalendarReq p);
    int deleteCalendar(Long calendarId);
}
