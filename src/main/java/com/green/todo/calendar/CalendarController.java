package com.green.todo.calendar;

import com.green.todo.calendar.model.req.*;
import com.green.todo.calendar.model.res.GetCalendarRes;
import com.green.todo.calendar.model.res.GetUserByEmailRes;
import com.green.todo.calendar.model.res.MemRes;
import com.green.todo.common.model.ResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/calendar")
@Tag(name = "캘린더 컨트롤러")
public class CalendarController {
    private final CalendarService service;

    @PostMapping
    @Operation(summary = "캘린더 생성", description = "<p>캘린더 생성하는 곳입니다요~!~!</p>" +
            "<p>모든 항목에 올바른 값 넣으셔야 합니다.</p>" +
            "<p>캘린더 이름은 1~20자 사이로 넣으셔야 합니다.(한글은 1개당 2자임)</p>" +
            "<p>색상은 약속된 색상만 사용합니다.</p>")
    @ApiResponse(responseCode = "200",description =
                    "<p>statusCode = 2 => 정상 </p>"+
                    "<p>statusCode = 4 => 생성된 캘린더 없음 및 오류 </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData =  (-1 => 생성 안됨 및 오류), (나머지 => 생성된 캘린더 id) </p>"
    )
    public ResultDto<Long> createCalendar(@RequestBody CreateCalendarReq p) {
        int code = 2;
        String msg = "캘린더 생성 완료";
        long result = -1;

        try {
            result = service.createCalendar(p);
        } catch (Exception e) {
            code = 4;
            msg = e.getMessage();
        }

        return ResultDto.<Long>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @GetMapping
    @Operation(summary = "캘린더 목록 가지고오기", description = "<strong>캘린더 목록을 불러온다요~!~!</strong>" +
            "<p>로그인한 user_id 값을 넣어주세요~!~!</p>")
    @ApiResponse(responseCode = "200",description =
                    "<p>statusCode = 2 => 정상</p>"+
                    "<p>statusCode = 4 => 오류난거임~!~! </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 가지고온 캘린더 목록~!~! </p>"
    )
    public ResultDto<List<GetCalendarRes>> getCalendarList(@Schema(example = "1") @RequestParam(name = "signed_user_id") String  signedUserId) {
        int code = 2;
        String msg = "캘린더 불러오기 완료";
        List<GetCalendarRes> result = null;

        try {
            result = service.getCalendarList(signedUserId);
        } catch (Exception e) {
            code = 4;
            msg = e.getMessage();
        }

        return ResultDto.<List<GetCalendarRes>>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @GetMapping("member")
    @Operation(summary = "캘린더 멤버 목록 가지고오기", description = "<strong>캘린더 멤버 목록을 불러온다요~!~!</strong>" +
            "<p>calendar_id 값을 넣어주세요~!~!</p>")
    @ApiResponse(responseCode = "200",description =
            "<p>statusCode = 2 => 정상</p>"+
                    "<p>statusCode = 4 => 오류난거임~!~! </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 가지고온 캘린더 멤버 목록~!~! </p>"
    )
    public ResultDto<List<MemRes>> getMemberList(@Schema(name = "calendar_id", example = "1") @RequestParam(name = "calendar_id") String calendarId,
                                                 @Schema(name = "owner_user_id", example = "1") @RequestParam(name = "owner_user_id") String signedUserId ) {
        int code = 2;
        String msg = "멤버 리스트 불러오기 완료";
        List<MemRes> result = null;

        try {
            result = service.getMemberList(calendarId, signedUserId);
        } catch (Exception e) {
            code = 4;
            msg = e.getMessage();
        }

        return ResultDto.<List<MemRes>>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @PutMapping
    @Operation(summary = "캘린더 정보 업데이트", description = "<p>캘린더 정보를 업데이트 한다요~!~!</p>" +
            "<p>캘린더 만들때 양식이랑 똑같이!!</p>"+
            "<p>넣은 부분만 수정하니까, 수정할 거만 넣으시면 됩니다~!~!</p>")
    @ApiResponse(responseCode = "200",description =
                    "<p>statusCode = 2 => 정상</p>"+
                    "<p>statusCode = 4 => 오류난거임~!~!</p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 수정된 캘린더의 id </p>"
    )
    public ResultDto<Long> updateCalendar(@RequestBody UpdateCalendarReq p) {
        int code = 2;
        String msg = "캘린더 수정 완료";
        long result = -1;

        try {
            result = service.updateCalendar(p);
        } catch (Exception e) {
            code = 4;
            msg = e.getMessage();
        }

        return ResultDto.<Long>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @DeleteMapping
    @Operation(summary = "캘린더 지우기", description = "<strong>캘린더를 지운다니깐요?</strong>" +
            "<p>지울 캘린더의 id 넣어주세요~!~!</p>" +
            "<p>누구에게서 캘린더를 지울지 유저id를 넣어주세요~!~!</p>" +
            "<strong>해당 유저한테서만 캘린더가 삭제됩니다</strong>" +
            "<p>**캘린더를 가지고 있는 마지막 유저가 삭제하면, 캘린더 또한 영원히 삭제됨!!!!**</p>")
    @ApiResponse(responseCode = "200",description =
                    "<p>statusCode = 2 => 정상</p>"+
                    "<p>statusCode = 4 => 오류난거임~!~!</p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 캘린더가 지워지면  1  나옵니다!. </p>"
    )
    public ResultDto<Integer> deleteCalendar(@ParameterObject @ModelAttribute DeleteCalendarReq p) {
        int code = 2;
        String msg = "캘린더 삭제 완료";
        int result = -1;

        try {
            result = service.deleteCalendar(p);
        } catch (Exception e) {
            code = 4;
            msg = e.getMessage();
        }

        return ResultDto.<Integer>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @PostMapping("plus")
    @Operation(summary = "캘린더에 유저 한명씩 추가", description = "<strong>캘린더를 볼 수 있는 유저 한명 추가!</strong>" +
            "<p>선택한 캘린더 id와, 추가할 유저 Email를 입력해 주세요~!~!</p>")
    @ApiResponse(responseCode = "200",description =
                    "<p>statusCode = 2 => 정상</p>"+
                    "<p>statusCode = 4 => 추가 못했거나, 오류난거임~!~!</p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 1명 추가되면, 추가된 사람 이름, user_id 나옴!</p>" +
                            "<p>추가된 사람이 없으면, null 나옴!</p>"
    )
    public ResultDto<GetUserByEmailRes> plusCalendarUser(@RequestBody PlusCalendarUserReq p) {
        int code = 2;
        String msg = "유저 추가 완료";
        GetUserByEmailRes result = null;

        try {
            result = service.plusCalendarUser(p);
        } catch (Exception e) {
            code = 4;
            msg = e.getMessage();
        }

        return ResultDto.<GetUserByEmailRes>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @Operation(summary = "캘린더 멤버 삭제", description = "<p>캘린더 맴버 삭제하는 곳입니다요~!~!</p>" +
            "<p>모든 항목에 올바른 값 넣으셔야 합니다.</p>" +
            "<p>캘린더를 만든 주인만 다른 유저를 삭제할 수 있습니다.</p>")
    @ApiResponse(responseCode = "200",description =
            "<p>statusCode = 2 => 정상 </p>"+
                    "<p>statusCode = 4 => 오류 </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = (0 => 주인이 아니라서 누굴 못지워서 0명 지워짐. 또는 지울 사람이 없어서 0명 지워짐.), " +
                    "(1 => 생성 성공) </p>"
    )
    @DeleteMapping("member")
    public ResultDto<Integer> deleteCalendarMember(@RequestBody DeleteCalendarMemberReq p) {
        int code = 2;
        String msg = "멤버 삭제 완료";
        int result = -1;

        try {
            result = service.deleteCalendarMember(p);
        } catch (Exception e) {
            code = 4;
            msg = e.getMessage();
        }

        return ResultDto.<Integer>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }
}
