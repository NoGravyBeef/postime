package com.green.todo.notice;

import com.green.todo.notice.model.NoticeContent;
import com.green.todo.notice.model.req.NoticeListPostReq;
import com.green.todo.notice.model.req.NoticePostReq;
import com.green.todo.notice.model.req.NoticeReq;
import com.green.todo.notice.model.req.NoticeUpdateReq;
import com.green.todo.notice.model.res.NoticeGetRes;
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
    public void newBoardNotice(NoticeReq p, String boardName) {
        contents.setCalendarName(mapper.getCalendarName(p.getCalendarId()));
        contents.setContent(boardName);

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
    public void newCommentNotice(NoticeReq p, long boardId, String content) {
        contents.setBoardName(mapper.getBoardName(boardId));
        contents.setContent(content);

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
    public List<NoticeGetRes> getNoticeList (String signedUserId) {
        Long userId = null;
        try {
            userId = Long.parseLong(signedUserId);
        } catch (Exception e) {
            throw new RuntimeException("파싱 에러");
        }
        if(userId == null || userId < 0){
            throw new RuntimeException("로그인된 유저의 PK를 입력해주세요.");
        }
        List<NoticeGetRes> result = mapper.getNoticeList(userId);
        return result;
    }

// update ======================================================================
    public void noticeRead(List<NoticeGetRes> p, String signedUserId) {
        Long userId = null;
        try {
            userId = Long.parseLong(signedUserId);
            for(NoticeGetRes notice : p) {
                NoticeUpdateReq req = new NoticeUpdateReq();
                req.setNoticeId(notice.getNoticeId());
                req.setSignedUserId(userId);
                mapper.updateNotice(req);
            }
        } catch (Exception e) {
            throw new RuntimeException("알림 읽음처리 실패");
        }
    }


}
