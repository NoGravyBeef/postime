package com.green.todo.calendar;

import com.green.todo.calendar.model.req.DeleteCalendarServiceReq;
import com.green.todo.calendar.model.req.*;
import com.green.todo.calendar.model.res.GetCalendarRes;
import com.green.todo.calendar.model.res.GetUserByEmailRes;
import com.green.todo.calendar.model.res.MemRes;
import com.green.todo.calendar.module.CountLengthUtil;
import com.green.todo.common.CommonUtils;
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
    private final CommonUtils commonUtils;
    private final NoticeService noticeService;

    /*-------------------------캘린더 생성 service-------------------------*/
    @Transactional
    public long createCalendar(CreateCalendarReq p) throws Exception{

        if (p.getTitle() == null || p.getColor() == null ||
                p.getSignedUserId() == null) {
            throw new RuntimeException("양식 모두 채워주세요.");
        }

        int length = CountLengthUtil.countLength(p.getTitle());

        if (length < 1 || length > 20) {
            throw new RuntimeException("제목 양식 맞춰주세요.");
        }

        if (!commonUtils.validateColor(p.getColor())) {
            throw new RuntimeException("색상 양식 맞춰주세요.");
        }

        long result = 0;
        try {
            result = mapper.createCalendar(p);
        } catch (Exception e) {
            throw new RuntimeException("캘린더 생성 실패");
        }

        if (result == 0) {
            throw new RuntimeException("캘린더가 제대로 생성되지 않았습니다.");
        }

        try {
            String path = String.format("calendar/%d", p.getCalendarId());
            customFileUtils.makeFolder(path);
        } catch (Exception e) {
            throw new RuntimeException("캘린더 폴더 생성 오류");
        }

        int result2 = 0;
        try {
            result2 = mapper.plusCalendarUserById(p.getCalendarId(), p.getSignedUserId());
        } catch (Exception e) {
            throw new RuntimeException("캘린더에 로그인된 사용자가 들어가지 않았습니다.");
        }

        if (result2 == 0) {
            throw new RuntimeException("캘린더에 로그인된 사용자가 들어가지 않았습니다.");
        }



        return p.getCalendarId();
    }

    /*-------------------------캘린더 불러오기 service-------------------------*/
    public List<GetCalendarRes> getCalendarList(String signedUserId) throws Exception{
        List<GetCalendarRes> result = null;

        Long signed_user_id = null;
        try {
            signed_user_id = Long.parseLong(signedUserId);
        } catch (NumberFormatException e) {
            throw new RuntimeException("파싱에러");
        }

        try {
            result = mapper.getCalendarList(signed_user_id);
        } catch (Exception e) {
            throw new RuntimeException("캘린더 불러오기 실패");
        }

        return result;
    }

    /*-------------------------캘린더 멤버 불러오기 service-------------------------*/
    public List<MemRes> getMemberList(String calendarId,
                                      String signedUserId) {
        if (calendarId == null) {
            throw new RuntimeException("캘린더 id가 선택되지 않았습니다.");
        }
        if(signedUserId == null) {
            throw new RuntimeException("사용자 id가 없습니다.");
        }

        Long calendar_id = Long.parseLong(calendarId);
        Long signedUserIdLong = Long.parseLong(signedUserId);

        List<MemRes> result = null;
        try {
            result = mapper.getMemberList(calendar_id, signedUserIdLong);
        } catch (Exception e) {
            throw new RuntimeException("멤버 불러오기 실패");
        }
        if (result == null) {
            throw new RuntimeException("불러온 멤버가 없습니다.");
        }

        return result;
    }

    /*-------------------------캘린더 수정 service-------------------------*/
    public long updateCalendar(UpdateCalendarReq p) throws Exception{
        if (p.getCalendarId() == null) {
            throw new RuntimeException("업데이트 양식 맞춰주세요.");
        }



        if (p.getTitle() != null) {
            int length = CountLengthUtil.countLength(p.getTitle());
            if (length == 0 || length > 20) {
                throw new RuntimeException("제목 양식 맞춰주세요.");
            }
        }

        if (p.getColor() != null) {
            if (!commonUtils.validateColor(p.getColor())) {
                throw new RuntimeException("색상 양식 맞춰주세요.");
            }
        }

        if (p.getTitle() == null && p.getColor() == null) {
            throw new RuntimeException("변경할 값이 없습니다.");
        }

        int result = 0;
        try {
            result = mapper.updateCalendar(p);
        } catch (Exception e) {
            throw new RuntimeException("캘린더 수정 실패");
        }

        if (result == 0) {
            throw new RuntimeException("캘린더 수정 제대로 되지 않았습니다.");
        }

        return p.getCalendarId();
    }

    /*-------------------------캘린더 삭제 service-------------------------*/
    @Transactional
    public int deleteCalendar(DeleteCalendarReq p) throws Exception{

        if (p.getSignedUserId() == null){
            throw new RuntimeException("어떤 유저인지 선택이 안됐습니다.");
        }
        if (p.getCalendarId() == null) {
            throw new RuntimeException("삭제할 캘린더가 선택되지 않았습니다.");
        }

        Long signedUserId = null;
        Long calendarId = null;
        try {
            signedUserId = Long.parseLong(p.getSignedUserId());
            calendarId = Long.parseLong(p.getCalendarId());
        } catch (NumberFormatException e) {
            throw new RuntimeException("파싱 에러");
        }


        int result = 0;
        try {
            result = mapper.deleteCalendar(signedUserId, calendarId);
        } catch (Exception e) {
            throw new RuntimeException("캘린더 삭제 실패");
        }
        if (result == 0) {
            throw new RuntimeException("삭제된 캘린더가 없습니다.");
        }

        try {
            mapper.deleteCalendarPermanent(calendarId);
            customFileUtils.deleteFolder(String.format("%scalendar/%d", customFileUtils.uploadPath, calendarId));
        } catch (Exception e) {
            throw new RuntimeException("캘린더 삭제 실패");
        }

        return result;
    }

    /*-------------------------유저 추가 service-------------------------*/
    @Transactional
    public GetUserByEmailRes plusCalendarUser(PlusCalendarUserReq p) throws Exception {
        if (p.getCalendarId() == null || p.getUserEmail() == null || p.getUserEmail().isEmpty()) {
            throw new RuntimeException("양식이 올바르지 않습니다.");
        }

        GetUserByEmailRes res = null;
        try {
            res = mapper.getUserIdByEmail(p.getUserEmail());
        } catch (Exception e) {
            throw new RuntimeException("존재하지 않는 유저 Email 입니다.");
        }

        NoticeReq req = null;
        try {
            req = new NoticeReq(p.getCalendarId(), res.getUserId());
        } catch (Exception e) {
            throw new RuntimeException("알림 보내기 실패");
        }

        noticeService.newMemberNotice(req);

        int result = 0;
        try {
            result = mapper.plusCalendarUserById(p.getCalendarId(), res.getUserId());
        } catch (Exception e) {
            throw new RuntimeException("유저 추가 실패");
        }

        if(result == 0){
            throw  new RuntimeException("추가된 유저가 없습니다.");
        }

        return res;
    }

    /*-------------------------유저 삭제(만든이만 가능한거) service-------------------------*/
    public int deleteCalendarMember(DeleteCalendarMemberReq p) {
        if (p.getCalendarId() == null || p.getUserId() == null || p.getSignedUserId() == null) {
            throw new RuntimeException("양식값들을 모두 채워주세요.");
        }

        long createUser = mapper.selecteCalendar(p.getCalendarId());

        if(createUser != p.getSignedUserId()){
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        int result = 0;
        try {
            result = mapper.deleteCalendarMember(p);
        } catch (Exception e) {
            throw new RuntimeException("멤버 삭제 실패");
        }

        return result;
    }
}
