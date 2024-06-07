package com.green.todo.calendar;

import com.green.todo.calendar.model.req.*;
import com.green.todo.calendar.model.res.GetCalendarRes;
import com.green.todo.calendar.model.res.GetUserByEmailRes;
import com.green.todo.calendar.model.res.MemRes;
import com.green.todo.calendar.module.CountLengthUtil;
import com.green.todo.common.CustomFileUtils;
import com.green.todo.notice.NoticeService;
import com.green.todo.notice.model.req.NoticeReq;
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
    private final CustomFileUtils customFileUtils;
    private final NoticeService noticeService;

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

        try {
            String path = String.format("calendar/%d", p.getCalendarId());
            customFileUtils.makeFolder(path);
        } catch (Exception e) {
            throw new RuntimeException("캘린더 폴더 생성 오류~!~!");
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



        return p.getCalendarId();
    }

    /*-------------------------캘린더 불러오기 service-------------------------*/
    public List<GetCalendarRes> getCalendarList(long signedUserId) throws Exception{
        List<GetCalendarRes> result = null;

        try {
            result = mapper.getCalendarList(signedUserId);
        } catch (Exception e) {
            throw new RuntimeException("캘린더 불러오기 쿼링 이슈~!~!");
        }

        return result;
    }

    /*-------------------------캘린더 멤버 불러오기 service-------------------------*/
    public List<MemRes> getMemberList(Long calendarId) {
        if (calendarId == null) {
            throw new RuntimeException("캘린더 id가 선택 안됐어요~!~!");
        }

        List<MemRes> result = null;
        try {
            result = mapper.getMemberList(calendarId);
        } catch (Exception e) {
            throw new RuntimeException("멤버 불러오기 쿼링 이슈~!~!");
        }
        if (result == null) {
            throw new RuntimeException("불러온 멤버가 없습니다~!~!");
        }

        return result;
    }

    /*-------------------------캘린더 수정 service-------------------------*/
    public long updateCalendar(UpdateCalendarReq p) throws Exception{
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

        if (p.getTitle() == null && p.getColor() == null) {
            throw new RuntimeException("변경할 값이 없는데요?");
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

        return p.getCalendarId();
    }

    /*-------------------------캘린더 삭제 service-------------------------*/
    @Transactional
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
            customFileUtils.deleteFolder(String.format("%scalendar/%d", customFileUtils.uploadPath, p.getCalendarId()));
        } catch (Exception e) {
            throw new RuntimeException("캘린더 삭제 쿼링 이슈~!~!");
        }

        return result;
    }

    /*-------------------------유저 추가 service-------------------------*/
    @Transactional
    public String plusCalendarUser(PlusCalendarUserReq p) throws Exception {
        if (p.getCalendarId() == null || p.getUserEmail() == null) {
            throw new RuntimeException("유저 추가 양식 부족함~!~!");
        }

        GetUserByEmailRes res = null;
        try {
            res = mapper.getUserIdByEmail(p.getUserEmail());
        } catch (Exception e) {
            throw new RuntimeException("존재하지 않는 유저 Email 입니다~!~!");
        }

        NoticeReq req = null;
        try {
            GetUserByEmailRes user = mapper.getUserIdByEmail(p.getUserEmail());
            req = new NoticeReq(p.getCalendarId(), user.getUserId());
        } catch (Exception e) {
            throw new RuntimeException("알림 관련 쿼리 이슈~!~!");
        }

        noticeService.newMemberNotice(req);

        int result = 0;
        try {
            result = mapper.plusCalendarUserById(p.getCalendarId(), res.getUserId());
        } catch (Exception e) {
            throw new RuntimeException("유저 추가 쿼링 이슈~!~!");
        }

        if(result == 0){
            throw  new RuntimeException("추가된 유저가 없습니다~!~!");
        }

        return res.getName();
    }

    /*-------------------------유저 삭제(만든이만 가능한거) service-------------------------*/
    public int deleteCalendarMember(DeleteCalendarMemberReq p) {
        if (p.getCalendarId() == null || p.getUserId() == null || p.getSignedUserId() == null) {
            throw new RuntimeException("양식값들을 모두 채워주세요~!~!");
        }

        int result = 0;
        try {
            result = mapper.deleteCalendarMember(p);
        } catch (Exception e) {
            throw new RuntimeException("멤버 삭제 쿼링 이슈~!~!");
        }

        return result;
    }
}
