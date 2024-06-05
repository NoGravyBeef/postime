package com.green.todo.notice;

import com.green.todo.notice.model.NoticeContent;
import com.green.todo.notice.model.req.NoticeListPostReq;
import com.green.todo.notice.model.req.NoticePostReq;
import com.green.todo.notice.model.req.NoticeReq;
import com.green.todo.notice.model.req.NoticeUpdateReq;
import com.green.todo.notice.model.res.NoticeGetRes;
import com.green.todo.notice.model.res.NoticeListRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeMapper mapper;
    NoticeContent contents = new NoticeContent();

// post ======================================================================

    // NoticeReq = 캘린더 PK, 새로 추가된 유저 PK
    public void newMemberNotice(NoticeReq p) {
        contents.setCalendarName(mapper.getCalendarName(p.getCalendarId()));
        contents.setNewUserName(mapper.getUserName(p.getUserId()));

        // 기존에 있던 사람들한테 알림 보내주기
        NoticePostReq insNotice1 = new NoticePostReq();
        insNotice1.setCalendarId(p.getCalendarId());
        insNotice1.setContent(contents.getNewMemToOther());
        mapper.insertNotice(insNotice1);
        List<Long> member = mapper.getCalendarMember(p.getCalendarId());
        for(long mem : member) {
            NoticeListPostReq insList = new NoticeListPostReq(insNotice1.getNoticeId(), mem);
            mapper.insertNoticeList(insList);
        }

        // 새로 추가된 사람한테 알림 보내주기
        NoticePostReq insNotice2 = new NoticePostReq();
        insNotice2.setCalendarId(p.getCalendarId());
        insNotice2.setContent(contents.getNewMemToOne());
        mapper.insertNotice(insNotice2);
        NoticeListPostReq insList = new NoticeListPostReq(insNotice2.getNoticeId(), p.getUserId());
        mapper.insertNoticeList(insList);
    }

    // NoticeReq = 캘린더 PK, 보드를 추가한 유저의 PK
    public void newBoardNotice(NoticeReq p) {
        contents.setCalendarName(mapper.getCalendarName(p.getCalendarId()));

        NoticePostReq insNotice = new NoticePostReq();
        insNotice.setCalendarId(p.getCalendarId());
        insNotice.setContent(contents.getNewBoard());
        mapper.insertNotice(insNotice);
        List<Long> member = mapper.getCalendarMember(p.getCalendarId());
        for(long mem : member) {
            if(mem != p.getUserId()){  // 보드 작성자한테는 알림 x
                NoticeListPostReq insList = new NoticeListPostReq(insNotice.getNoticeId(), mem);
                mapper.insertNoticeList(insList);
            }
        }
    }

    // NoticeReq = 캘린더PK, 댓글을 추가한 유저의 PK  + 보드 PK
    public void newCommentNotice(NoticeReq p, long boardId) {
        contents.setBoardName(mapper.getBoardName(boardId));

        NoticePostReq insNotice = new NoticePostReq();
        insNotice.setCalendarId(p.getCalendarId());
        insNotice.setContent(contents.getNewComment());
        mapper.insertNotice(insNotice);
        List<Long> member = mapper.getCalendarMember(p.getCalendarId());
        for(long mem : member) {
            if(mem != p.getUserId()){  // 댓글 작성자한테는 알림 x
                NoticeListPostReq insList = new NoticeListPostReq(insNotice.getNoticeId(), mem);
                mapper.insertNoticeList(insList);
            }
        }
    }

// get ======================================================================
    public NoticeListRes getNoticeList (Long userId) {
        if(userId == null || userId < 0) {
            throw new RuntimeException("유저 PK를 입력해주세요.");
        }
        int notRead = mapper.notRead(userId);
        List<NoticeGetRes> list = mapper.getNoticeList(userId);
        NoticeListRes result = new NoticeListRes();
        result.setNotice(list);
        result.setNotRead(notRead);
        return result;
    }

// update ======================================================================
    public int noticeUpdate(NoticeUpdateReq p) {
        if(p.getNoticeId() == null || p.getNoticeId() < 0) {
            throw new RuntimeException("알림 PK를 입력해주세요.");
        }
        if(p.getSignedUserId() == null || p.getSignedUserId() < 0){
            throw new RuntimeException("로그인된 유저의 PK를 입력해주세요.");
        }
        int result = mapper.updateNotice(p);
        if(result != 1) {
            throw new RuntimeException("알림 업데이트 실패");
        }
        return result;
    }


}
