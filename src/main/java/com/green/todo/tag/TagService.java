package com.green.todo.tag;

import com.green.todo.common.CommonUtils;
import com.green.todo.tag.model.TagEntity;
import com.green.todo.tag.model.req.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagMapper mapper;
    private final CommonUtils utils;


    // List<Long> existTag : 태그 PK
    // List<TagPostReq> notExistTag : 캘린더PK, 이름, 색상
    @Transactional
    public int tagPost (List<Long> existTag, List<TagPostReq> notExistTag, long boardId) {
        if(existTag.isEmpty() && notExistTag.isEmpty()) {  // 태그 없음
            return 1;
        }
        List<Long> tags = new ArrayList<>();
        for(TagPostReq p : notExistTag) {
            tagTitleOk(p.getTitle(), p.getCalendarId());
            mapper.insertTag(p);
            tags.add(p.getTagId());
        }
        tags.addAll(existTag);
        BoardTagPostReq insertReq = new BoardTagPostReq();
        insertReq.setTags(tags);
        insertReq.setBoardId(boardId);
        int result = mapper.insertBoardTag(insertReq);
        return result;
    }

    @Transactional
    public int tagDelete(List<Long> deltagList, long boardId) {
        if(deltagList == null || deltagList.isEmpty()) {    // 삭제할 태그 없음
            return 1;
        }
        int result = delBoardTag(deltagList, boardId);   // 보드_태그 테이블에서 삭제
        deleteTagPermanent(deltagList);     // 태그 테이블에서 삭제
        return result;
    }

    // ==================
    public void tagTitleOk (String tagTitle, long calendarId) { // 태그 이름 알맞은지 확인
        if(tagTitle == null || tagTitle.isEmpty()) {
            throw new RuntimeException("태그 이름을 입력해주세요.");
        }
        if(!utils.isWithinByteLimit(tagTitle, 20)) {
            throw new RuntimeException("태그의 이름이 너무 깁니다. (최대 20Byte)");
        }
        TagGetReq tagGet = new TagGetReq(tagTitle, calendarId);
        TagEntity tag = mapper.getTagForCheckTitle(tagGet);
        if(tag != null) {
            throw new RuntimeException("태그의 이름이 중복됩니다.");
        }
    }

    public void deleteTagPermanent(List<Long> deltagList) {
        try {
            for(Long tagId : deltagList) {
                mapper.deleteTagPermanent(tagId);
            }
        } catch (Exception e) {
            throw new RuntimeException("tag 삭제 오류");
        }
    }

    public int delBoardTag(List<Long> delList, long boardId) {
        List<BoardTagDeleteReq> list = new ArrayList<>();
        try {
            for(Long item : delList) {
                BoardTagDeleteReq p = new BoardTagDeleteReq();
                p.setBoardId(boardId);
                p.setTagId(item);
                list.add(p);
            }
        } catch (Exception e) {
            throw new RuntimeException("board_tag 삭제 오류");
        }
        return mapper.deleteBoardTag(list);
    }

}