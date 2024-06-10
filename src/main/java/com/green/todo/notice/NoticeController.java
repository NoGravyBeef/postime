package com.green.todo.notice;

import com.green.todo.common.CommonUtils;
import com.green.todo.common.model.ResultDto;
import com.green.todo.notice.model.req.NoticeUpdateReq;
import com.green.todo.notice.model.res.NoticeListRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import static com.green.todo.common.model.ResultDto.resultDto;

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
    @Operation(summary = "알림 목록 가지고오기", description = "<strong>필수 입력입니다.~!~!</strong>" +
            "<p>알림 목록을 가져옵니다~!~!</p>")
    @ApiResponse(responseCode = "2",description =
            "<p>statusCode = 2 => 정상</p>"+
                    "<p>statusCode = 4 => 오류난거임~!~! </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 가지고온 알림 목록~!~! </p>"
    )
    public ResultDto<NoticeListRes> noticeList(@RequestParam("signed_user_id") String signedUserId) {
        utils.init("알림 목록을 불러왔습니다.");
        NoticeListRes result = null;
        try {
            result = service.getNoticeList(signedUserId);
        } catch (Exception e) {
            utils.noAcceptable(e);
        }
        return resultDto(utils.getCode(), utils.getMsg(), result);
    }

    @PutMapping
    @Operation(summary = "알림 읽음처리 (업데이트)", description = "<strong>모두 필수 입력입니다~!~!</strong>" +
            "<p>성공하면 1, 실패하면 0 리턴합니다~!~!</p>")
    @ApiResponse(responseCode = "2",description =
            "<p>statusCode = 2 => 정상</p>"+
                    "<p>statusCode = 4 => 오류난거임~!~! </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 1을 리턴합니다~!~! </p>"
    )
    public ResultDto<Integer> readNotice(@RequestBody NoticeUpdateReq p) {
        utils.init("알림을 읽음으로 처리하였습니다.");
        int result = 0;
        try {
            result = service.noticeUpdate(p);
        } catch (Exception e) {
            utils.noAcceptable(e);
        }
        return resultDto(utils.getCode(), utils.getMsg(), result);
    }


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
