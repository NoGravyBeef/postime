package com.green.todo.notice;

import com.green.todo.common.CommonUtils;
import com.green.todo.common.model.ResultDto;
import com.green.todo.notice.model.req.NoticeUpdateReq;
import com.green.todo.notice.model.res.NoticeGetRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("api/notice")
@Tag(name = "알림 컨트롤러")
public class NoticeController {
    private final NoticeService service;
    private final CommonUtils utils;


    @GetMapping
    @Operation(summary = "읽지 않은 알림 목록 가지고오기", description = "<strong>필수 입력입니다.~!~!</strong>" +
            "<p>유저의 읽지 않은 알림 목록을 가져옵니다~!~!</p>" +
            "<p>가져온 알림은 모두 읽음처리 됩니다~!~!</p>")
    @ApiResponse(responseCode = "200",description =
            "<p>statusCode = 2 => 정상</p>"+
                    "<p>statusCode = 4 => 오류난거임~!~! </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 가지고온 알림 목록~!~! </p>"
    ) // 읽음 -> 삭제
    @Transactional
    public ResultDto<List<NoticeGetRes>> noticeList(@Schema(example = "1") @RequestParam("signed_user_id") String signedUserId) {
        utils.init("알림 목록을 불러왔습니다.");
        List<NoticeGetRes> result = null;
        try {
            result = service.getNoticeList(signedUserId);
            if(result != null && !result.isEmpty()) {
                service.deleteNotice(result, signedUserId);
            }
        } catch (Exception e) {
            utils.noAcceptable(e);
        }
        return new ResultDto<>(utils.getCode(), utils.getMsg(), result);
    }


    // 읽음 -> 업데이트
//    public ResultDto<List<NoticeGetRes>> noticeList(@Schema(example = "1") @RequestParam("signed_user_id") String signedUserId) {
//        utils.init("알림 목록을 불러왔습니다.");
//        List<NoticeGetRes> result = null;
//        try {
//            result = service.getNoticeList(signedUserId);
//            service.noticeRead(result, signedUserId);
//        } catch (Exception e) {
//            utils.noAcceptable(e);
//        }
//        return new ResultDto<>(utils.getCode(), utils.getMsg(), result);
//    }


    // ===================================================
//    @PostMapping("mem")
//    @Operation(summary = "테스트 post 1", description = "캘린더에 새로운 멤버가 추가되었을 때")
//    public int test (@RequestBody NoticeReq p){
//        service.newMemberNotice(p);
//        return 0;
//    }
//    @PostMapping("board")
//    @Operation(summary = "테스트 post 2", description = "새로운 일정 추가")
//    public int testboard (@RequestBody NoticeReq p){
//        service.newBoardNotice(p);
//        return 0;
//    }
//    @PostMapping("comment")
//    @Operation(summary = "테스트 post 3", description = "새로운 댓글 추가")
//    public int testcomment (@RequestBody NoticeReq p){
//        service.newCommentNotice(p,2);
//        return 0;
//    }
}
