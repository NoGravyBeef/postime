package com.green.todo.calendar;

import com.green.todo.calendar.model.req.CreateCalendarReq;
import com.green.todo.calendar.model.req.DeleteCalendarReq;
import com.green.todo.calendar.model.req.PlusCalendarUserReq;
import com.green.todo.calendar.model.req.UpdateCalendarReq;
import com.green.todo.calendar.model.res.GetCalendarRes;
import com.green.todo.calendar.model.res.GetUserByEmailRes;
import com.green.todo.calendar.module.CountLengthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CalendarService {
    private final CalendarMapper mapper;

    /*-------------------------캘린더 생성 service-------------------------*/
    @Transactional
    public long createCalendar(CreateCalendarReq p) throws Exception{

        if (p.getTitle() == null || p.getColor() == null ||
                p.getSignedUserId() == null) {
            throw new RuntimeException("양식 모두 채워주세요~!~!");
        }

        int length = CountLengthUtil.countLength(p.getTitle());

        if (length < 1 || length > 20) {
            throw new RuntimeException("제목 양식 맞춰주세요~!~!");
        }

        if (1 > p.getColor() || p.getColor() > 9) {
            throw new RuntimeException("색상 양식 맞춰주세요~!~!");
        }

        long result = 0;
        try {
            result = mapper.createCalendar(p);
        } catch (Exception e) {
            throw new RuntimeException("캘린더 생성 쿼링 이슈~!~!");
        }

        if (result == 0) {
            throw new RuntimeException("캘린더가 제대로 생성되지 않았습니다~!~!");
        }

        int result2 = 0;
        try {
            result2 = mapper.plusCalendarUserById(p.getCalendarId(), p.getSignedUserId());
        } catch (Exception e) {
            throw new RuntimeException("캘린더에 로그인된 사용자가 안들어감~!~!");
        }

        if (result2 == 0) {
            throw new RuntimeException("캘린더에 로그인된 사용자가 안들어감~!~!");
        }



        return result;
    }

    /*-------------------------캘린더 불러오기 service-------------------------*/
    public List<GetCalendarRes> getCalendarList(long signedUserId) throws Exception{
        List<GetCalendarRes> result = null;

        try {
            result = mapper.getCalendarList(signedUserId);
        } catch (Exception e) {
            throw new RuntimeException("캘린더 불러오기 쿼링 이슈~!~!");
        }

        if (result == null) {
            throw new RuntimeException("가지고온 캘린더가 0개임~!~!");
        }

        return result;
    }

    /*-------------------------캘린더 수정 service-------------------------*/
    public int updateCalendar(UpdateCalendarReq p) throws Exception{
        if (p.getCalendarId() == null) {
            throw new RuntimeException("업데이트 양식 맞춰주세요~!~!");
        }

        if (p.getTitle() != null) {
            int length = CountLengthUtil.countLength(p.getTitle());
            if (length == 0 || length > 20) {
                throw new RuntimeException("제목 양식 맞춰주세요~!~!");
            }
        }

        if (p.getColor() != null) {
            if (p.getColor() < 1 || p.getColor() > 9) {
                throw new RuntimeException("색상 양식 맞춰주세요~!~!");
            }
        }

        int result = 0;
        try {
            result = mapper.updateCalendar(p);
        } catch (Exception e) {
            throw new RuntimeException("캘린더 수정 쿼링 이슈~!~!");
        }

        if (result == 0) {
            throw new RuntimeException("캘린더 수정 제대로 안됨~!~!");
        }

        return result;
    }

    /*-------------------------캘린더 삭제 service-------------------------*/
    public int deleteCalendar(DeleteCalendarReq p) throws Exception{

        if (p.getSignedUserId() == null){
            throw new RuntimeException("어떤 유저인지 선택이 안됐습니다~!~!");
        }
        if (p.getCalendarId() == null) {
            throw new RuntimeException("삭제할 캘린더가 선택안됨~!~!");
        }

        int result = 0;
        try {
            result = mapper.deleteCalendar(p);
        } catch (Exception e) {
            throw new RuntimeException("캘린더 삭제 쿼링 이슈~!~!");
        }
        if (result == 0) {
            throw new RuntimeException("삭제된 캘린더가 없습니다~!~!");
        }


        try {
            mapper.deleteCalendarPermanent(p.getCalendarId());
        } catch (Exception e) {
            throw new RuntimeException("캘린더 삭제 쿼링 이슈~!~!");
        }

        return result;
    }

    /*-------------------------유저 추가 service-------------------------*/
    public String plusCalendarUser(PlusCalendarUserReq p) throws Exception {
        if (p.getSelectedCalendarId() == null || p.getUserEmail() == null) {
            throw new RuntimeException("유저 추가 양식 부족함~!~!");
        }

        GetUserByEmailRes res = null;
        try {
            res = mapper.getUserIdByEmail(p.getUserEmail());
        } catch (Exception e) {
            throw new RuntimeException("존재하지 않는 유저 Email 입니다~!~!");
        }

        int result = 0;
        try {
            result = mapper.plusCalendarUserById(p.getSelectedCalendarId(), res.getUserId());
        } catch (Exception e) {
            throw new RuntimeException("유저 추가 쿼링 이슈~!~!");
        }

        if(result == 0){
            throw  new RuntimeException("추가된 유저가 없습니다~!~!");
        }


        return res.getName();
    }
}
