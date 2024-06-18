package com.green.todo.board;

import com.green.todo.board.model.req.*;
import com.green.todo.board.model.res.*;
import com.green.todo.common.model.ResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/board")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "보드 컨트롤러")
public class BoardController {
    private final BoardService service;

    @PostMapping
    @Operation(summary = "보드 생성", description = "<p>보드 생성하는 곳입니다요~!~!</p>" +
            "<p>모든 항목에 올바른 값 넣으셔야 합니다.</p>" +
            "<p>제목은 1~20자 사이로 넣으셔야 합니다.(한글은 1개당 2자임)</p>" +
            "<p>본문은 1~1000자 사이로 넣으셔야 합니다.</p>")
    @ApiResponse(responseCode = "200",description =
            "<p>statusCode = 2 => 정상 </p>"+
                    "<p>statusCode = 4 => 생성된 보드 없음 및 오류 </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData =  (-1 => 생성 안됨 및 오류), (나머지 => 생성된 보드 id) </p>"
    )
    public ResultDto<Long> createBoard(@RequestPart CreateBoardReq p
                                       ,@RequestPart(required = false) List<MultipartFile> files) {
        int code = 2;
        String msg = "board 업로드 완료";
        long result = -1;
        log.info("보드 정보{}",p);
        try {
            result = service.createBoard(p, files);
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
    @Operation(summary = "보드 한개 정보 가지고오기", description = "<strong>보드 한개 정보를 불러온다요~!~!</strong>" +
            "<p>선택된 보드 id 값을 넣어주세요~!~!</p>")
    @ApiResponse(responseCode = "200",description =
            "<p>statusCode = 2 => 정상</p>"+
                    "<p>statusCode = 4 => 오류난거임~!~! </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 가지고온 보드 정보 </p>"
    )
    public ResultDto<GetBoardRes> getBoardInfo (@Schema(example = "1", description = "선택한 보드의 id")
                                                    @RequestParam(name = "board_id") String boardId) {

        int code = 2;
        String msg = "board 정보 불러오기 완료";
        GetBoardRes result = null;

        try {
            result = service.getBoardInfo(boardId);
        } catch (Exception e) {
            code = 4;
            msg = e.getMessage();
        }

        return ResultDto.<GetBoardRes>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @GetMapping("mini")
    @Operation(summary = "달력에서 보여줄 보드 목록 가지고오기", description = "<strong>달력에서 보여줄 보드 목록 가지고옵니다.</strong>" +
            "<p>로그인한 user_id 값을 넣어주세요~!~!</p>")
    @ApiResponse(responseCode = "200",description =
            "<p>statusCode = 2 => 정상</p>"+
                    "<p>statusCode = 4 => 오류난거임~!~! </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = id 묶음, 보드 내용 </p>"
    )
    public ResultDto<List<GetViewCalendarRes>> getBoardMiniList(@Schema(example = "1") @RequestParam(name = "signed_user_id") String signedUserId) {

        int code = 2;
        String msg = "board mini 리스트 불러오기 완료";
        List<GetViewCalendarRes> result = null;

        try {
            result = service.getBoardMiniList(signedUserId);
        } catch (Exception e) {
            code = 4;
            msg = e.getMessage();
        }

        return ResultDto.<List<GetViewCalendarRes>>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @GetMapping("done")
    @Operation(summary = "완료된 board 목록 가지고오기", description = "<strong>완료된 board 목록을 불러온다요~!~!</strong>" +
            "<p>로그인한 user_id 값을 넣어주세요~!~!</p>")
    @ApiResponse(responseCode = "200",description =
            "<p>statusCode = 2 => 정상</p>"+
                    "<p>statusCode = 4 => 오류난거임~!~! </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 가지고온 완료된 board 목록~!~! </p>"
    )
    public ResultDto<List<GetBoardMiniRes>> getBoardDoneList(@Schema(example = "1") @RequestParam(name = "signed_user_id") String signedUserId) {
        int code = 2;
        String msg = "완료된 board 불러오기 완료";
        List<GetBoardMiniRes> result = null;

        try {
            result = service.getBoardDoneList(signedUserId);
        } catch (Exception e) {
            code = 4;
            msg = e.getMessage();
        }

        return ResultDto.<List<GetBoardMiniRes>>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @GetMapping("deleted")
    @Operation(summary = "삭제된 board 목록 가지고오기", description = "<strong>삭제된 board 목록을 불러온다요~!~!</strong>" +
            "<p>로그인한 user_id 값을 넣어주세요~!~!</p>")
    @ApiResponse(responseCode = "200", description = "<p>statusCode = 2 => 정상</p>"+
            "<p>statusCode = 4 => 오류난거임~!~! </p>" +
            "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
            "<p>resultData = 가지고온 삭제된 board 목록~!~! </p>")

    public ResultDto<List<GetBoardMiniRes>> getBoardDeletedList(@Schema(example = "1") @RequestParam(name = "signed_user_id") String signedUserId) {
        int code = 2;
        String msg = "삭제된 board 불러오기 완료";
        List<GetBoardMiniRes> result = null;

        try {
            result = service.getBoardDeletedList(signedUserId);
        } catch (Exception e) {
            code = 4;
            msg = e.getMessage();
        }

        return ResultDto.<List<GetBoardMiniRes>>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @GetMapping("search")
    @Operation(summary = "검색한 board 목록 가지고오기", description = "<strong>검색한 board 목록을 불러온다요~!~!</strong>" +
            "<p>검색어를 넣어주세요~!~!</p>")
    @ApiResponse(responseCode = "200", description = "<p>statusCode = 2 => 정상</p>"+
            "<p>statusCode = 4 => 오류난거임~!~! </p>" +
            "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
            "<p>resultData = 검색된 board 목록~!~! </p>")
    public ResultDto<List<GetBoardSearchRes>> getBoardSearchList(@Schema(example = "1") @RequestParam(name = "signed_user_id") String signedUserId
                                                                    , @Schema(example = "search_word") @RequestParam(required = false, name = "search_word") String searchWord) {
        int code = 2;
        String msg = "검색한 board 불러오기 완료";
        List<GetBoardSearchRes> result = null;

        try {
            result = service.getBoardSearchList(searchWord, signedUserId);
        } catch (Exception e) {
            code = 4;
            msg = e.getMessage();
        }

        return ResultDto.<List<GetBoardSearchRes>>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @GetMapping("todo")
    @Operation(summary = "todo 리스트 가지고오기", description = "<strong>todo 리스트 가지고오기~!~!</strong>" +
            "<p>로그인한 user_id 값을 넣어주세요~!~!</p>")
    @ApiResponse(responseCode = "200",description =
            "<p>statusCode = 2 => 정상</p>"+
                    "<p>statusCode = 4 => 오류난거임~!~! </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 가지고온 todo 리스트 목록~!~! </p>"
    )
    public ResultDto<TodoListRes> getBoardTodoList(@Schema(example = "1") @RequestParam(name = "signed_user_id") String signedUserId) {


        int code = 2;
        String msg = "todo 리스트 불러오기 완료";
        TodoListRes result = null;

        try {
            result = service.getBoardTodoList(signedUserId);
        } catch (Exception e) {
            code = 4;
            msg = e.getMessage();
        }

        return ResultDto.<TodoListRes>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @PutMapping
    @Operation(summary = "보드 정보 업데이트", description = "<p>보드 정보를 업데이트 한다요~!~!</p>" +
            "<p>보드 만들때 양식이랑 똑같이!!</p>"+
            "<p>넣은 부분만 수정하니까, 수정할 거만 넣으시면 됩니다~!~!</p>")
    @ApiResponse(responseCode = "200",description =
            "<p>statusCode = 2 => 정상</p>"+
                    "<p>statusCode = 4 => 오류난거임~!~!</p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 수정된 보드의 id </p>"
    )
    public ResultDto<Long> updateBoard(@RequestPart(required = false) List<MultipartFile> files, @RequestPart UpdateBoardReq p) {
        int code = 2;
        String msg = "보드 수정 완료";
        long result = -1;

        try {
            result = service.updateBoard(files, p);
        } catch (Exception e) {
            code = 4;
            msg = e.getMessage();
        }
        if (result == 0) {
            msg = "수정된 보드가 없습니다.";
        }

        return ResultDto.<Long>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @PatchMapping("state")
    @Operation(summary = "보드 상태 업데이트", description = "<p>보드 상태를 업데이트 한다요~!~!</p>" +
            "<p>모든 항목 무조건 넣으셔야 합니다~!~!</p>" +
            "<p>1 -> 진행중, 2 -> 완료, 3 -> 휴지통</p>")
    @ApiResponse(responseCode = "200",description =
            "<p>statusCode = 2 => 정상</p>"+
                    "<p>statusCode = 4 => 오류난거임~!~!</p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 성공하면 1, 실패 -1 </p>"
    )
    public ResultDto<Integer> updateBoardState(@RequestBody List<UpdateBoardStateReq> p) {
        int code = 2;
        String msg = "보드 상태 업데이트 완료";
        Integer result = -1;

        try {
            result = service.updateBoardState(p);
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

    @PatchMapping("dnd")
    @Operation(summary = "보드 dnd 업데이트", description = "<p>보드 dnd를 업데이트 한다요~!~!</p>" +
            "<p>모든 항목 무조건 넣으셔야 합니다~!~!</p>")
    @ApiResponse(responseCode = "200",description =
            "<p>statusCode = 2 => 정상</p>"+
                    "<p>statusCode = 4 => 오류난거임~!~!</p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 수정된 보드의 id </p>"
    )
    public ResultDto<Long> updateBoardDnD(@RequestBody UpdateBoardDnDReq p) {
        int code = 2;
        String msg = "보드 DnD 업데이트 완료";
        long result = -1;

        try {
            result = service.updateBoardDnD(p);
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
    @Operation(summary = "선택한 보드들 지우기", description = "<strong>보드들을 지운다니깐요?</strong>" +
            "<p>지울 보드의 id와 해당 보드의 캘린더 id를 담은 객체의 리스트를 넘겨주세요~!~!</p>" +
            "<p>**태그를 가지고 있는 마지막 보드가 삭제하면, 태그 또한 영원히 삭제됨!!!!**</p>")
    @ApiResponse(responseCode = "200",description =
            "<p>statusCode = 2 => 정상</p>"+
                    "<p>statusCode = 4 => 오류난거임~!~!</p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 보드가 지워지면  1  나옵니다!. </p>"
    )
    public ResultDto<Integer> deleteBoard(@RequestBody List<DeleteBoardReq> p) {
        int code = 2;
        String msg = "보드 삭제 완료";
        int result = -1;

        try {
            result = service.deleteBoard(p);
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

    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////

    @DeleteMapping("file")
    @Operation(summary = "파일 지우기", description = "<strong>파일 한개를 지운다니깐요?</strong>" +
            "<p>지울 파일의 id와 이름, 그리고 파일을 담은 보드와, 캘린더의 id</p>")
    @ApiResponse(responseCode = "200",description =
            "<p>statusCode = 2 => 정상</p>"+
                    "<p>statusCode = 4 => 오류난거임~!~!</p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 파일이 지워지면  1  나옵니다!. </p>"
    )
    public ResultDto<Integer> deleteFile(@RequestBody DeleteFileReq p) {
        int code = 2;
        String msg = "파일 삭제 완료";
        int result = -1;

        try {
            result = service.deleteFile(p);
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

//    @PostMapping("file")
//    @Operation(summary = "파일 생성", description = "<p>파일 생성하는 곳입니다요~!~!</p>" +
//            "<p>모든 항목에 올바른 값 넣으셔야 합니다.</p>")
//    @ApiResponse(responseCode = "200",description =
//            "<p>statusCode = 2 => 정상 </p>"+
//                    "<p>statusCode = 4 => 생성된 캘린더 없음 및 오류 </p>" +
//                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
//                    "<p>resultData = 파일 id와 이름 -> 성공, null -> 실패</p>"
//    )
//    public ResultDto<FileRes> createFile(@RequestPart MultipartFile file, @RequestPart CreateFileReq p) {
//        int code = 2;
//        String msg = "파일 생성 완료~!~!";
//        FileRes result = null;
//
//        try {
//            result = service.createFile(file, p);
//
//
//
//        } catch (Exception e) {
//            code = 4;
//            msg = e.getMessage();
//        }
//
//        return ResultDto.<FileRes>builder()
//                .statusCode(code)
//                .resultMsg(msg)
//                .resultData(result)
//                .build();
//    }

}
