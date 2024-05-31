package com.green.todo.calendar;

import com.green.todo.calendar.model.req.CreateCalendarReq;
import com.green.todo.calendar.model.req.DeleteCalendarReq;
import com.green.todo.calendar.model.req.PlusCalendarUserReq;
import com.green.todo.calendar.model.req.UpdateCalendarReq;
import com.green.todo.calendar.model.res.GetCalendarRes;
import com.green.todo.common.model.ResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/calendar")
public class CalendarController {
    private final CalendarService service;

    @PostMapping
    @Operation(summary = "캘린더 생성", description = "<p>캘린더 생성하는 곳입니다요~!~!</p>" +
            "<p>모든 항목에 올바른 값 넣으셔야 합니다.</p>" +
            "<p>캘린더 이름은 1~20자 사이로 넣으셔야 합니다.(한글은 1개당 2자임)</p>" +
            "<p>색상은 1~9번 사이의 숫자로 정합니다.</p>")
    @ApiResponse(responseCode = "200",description =
                    "<p>statusCode = 200 => 정상 </p>"+
                    "<p>statusCode = 406 => 생성된 캘린더 없음 및 오류 </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData =  (-1 => 생성 안됨 및 오류), (1 => 생성 성공) </p>"
    )
    public ResultDto<Long> createCalendar(@RequestBody CreateCalendarReq p) {
        HttpStatus code = HttpStatus.OK;
        String msg = "캘린더 생성 완료~!~!";
        long result = -1;

        try {
            result = service.createCalendar(p);
        } catch (Exception e) {
            code = HttpStatus.NOT_ACCEPTABLE;
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
                    "<p>statusCode = 200 => 정상</p>"+
                    "<p>statusCode = 406 => 불러온개 0개 이거나, 오류난거임~!~! </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 가지고온 캘린더 목록~!~! </p>"
    )
    public ResultDto<List<GetCalendarRes>> getCalendarList(@Schema(example = "1") @RequestParam(name = "signed_user_id") long signedUserId) {
        HttpStatus code = HttpStatus.OK;
        String msg = "캘린더 불러오기 완료~!~!";
        List<GetCalendarRes> result = null;

        try {
            result = service.getCalendarList(signedUserId);
        } catch (Exception e) {
            code = HttpStatus.NOT_ACCEPTABLE;
            msg = e.getMessage();
        }

        return ResultDto.<List<GetCalendarRes>>builder()
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
                    "<p>statusCode = 200 => 정상</p>"+
                    "<p>statusCode = 406 => 불러온개 0개 이거나, 오류난거임~!~!</p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 가지고온 캘린더 목록~!~! </p>"
    )
    public ResultDto<Integer> updateCalendar(@RequestBody UpdateCalendarReq p) {
        HttpStatus code = HttpStatus.OK;
        String msg = "캘린더 수정 완료~!~!";
        int result = -1;

        try {
            result = service.updateCalendar(p);
        } catch (Exception e) {
            code = HttpStatus.NOT_ACCEPTABLE;
            msg = e.getMessage();
        }

        return ResultDto.<Integer>builder()
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
                    "<p>statusCode = 200 => 정상</p>"+
                    "<p>statusCode = 406 => 불러온개 0개 이거나, 오류난거임~!~!</p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 캘린더가 지워지면  1  나옵니다!. </p>"
    )
    public ResultDto<Integer> deleteCalendar(@ParameterObject @ModelAttribute DeleteCalendarReq p) {
        HttpStatus code = HttpStatus.OK;
        String msg = "캘린더 삭제 완료~!~!";
        int result = -1;

        try {
            result = service.deleteCalendar(p);
        } catch (Exception e) {
            code = HttpStatus.NOT_ACCEPTABLE;
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
                    "<p>statusCode = 200 => 정상</p>"+
                    "<p>statusCode = 406 => 추가 못했거나, 오류난거임~!~!</p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 1명 추가되면, 추가된 사람 이름 나옴!</p>" +
                            "<p>추가된 사람이 없으면, null 나옴!</p>"
    )
    public ResultDto<String> plusCalendarUser(@RequestBody PlusCalendarUserReq p) {
        HttpStatus code = HttpStatus.OK;
        String msg = "유저 추가 완료~!~!";
        String result = null;

        try {
            result = service.plusCalendarUser(p);
        } catch (Exception e) {
            code = HttpStatus.NOT_ACCEPTABLE;
            msg = e.getMessage();
        }

        return ResultDto.<String>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }
}
