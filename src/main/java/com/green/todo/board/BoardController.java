package com.green.todo.board;

import com.green.todo.board.model.req.*;
import com.green.todo.board.model.res.*;
import com.green.todo.common.model.ResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Result;
import java.util.List;

@RestController
@RequestMapping("api/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService service;

    @PostMapping
    //TODO: 파일, 태그도 넣는거 해줘야함.
    public ResultDto<Long> createBoard(@RequestPart CreateBoardReq p
                                       ,@RequestPart(required = false) List<MultipartFile> files
                                       ) {
        HttpStatus code = HttpStatus.OK;
        String msg = "board 업로드 완료요~!~!";
        long result = -1;

        try {
            result = service.createBoard(p, files);
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
    public ResultDto<GetBoardRes> getBoardInfo (@RequestParam(name = "board_id") Long boardId) {

        HttpStatus code = HttpStatus.OK;
        String msg = "board 정보 불러오기 완료요~!~!";
        GetBoardRes result = null;

        try {
            result = service.getBoardInfo(boardId);
        } catch (Exception e) {
            code = HttpStatus.NOT_ACCEPTABLE;
            msg = e.getMessage();
        }

        return ResultDto.<GetBoardRes>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @GetMapping("mini")
    public ResultDto<List<GetBoardMiniRes>> getBoardMiniListByMonth(@RequestParam(name = "user_id") Long signedUserId, @RequestParam Integer month) {

        HttpStatus code = HttpStatus.OK;
        String msg = "board mini 리스트 불러오기 완료요~!~!";
        List<GetBoardMiniRes> result = null;

        try {
            result = service.getBoardMiniList(signedUserId, month);
        } catch (Exception e) {
            code = HttpStatus.NOT_ACCEPTABLE;
            msg = e.getMessage();
        }

        return ResultDto.<List<GetBoardMiniRes>>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @GetMapping("done")
    public ResultDto<List<GetBoardMiniRes>> getBoardDoneList(@RequestParam(name = "user_id") Long signedUserId) {
        HttpStatus code = HttpStatus.OK;
        String msg = "완료된 board 불러오기 완료요~!~!";
        List<GetBoardMiniRes> result = null;

        try {
            result = service.getBoardDoneList(signedUserId);
        } catch (Exception e) {
            code = HttpStatus.NOT_ACCEPTABLE;
            msg = e.getMessage();
        }

        return ResultDto.<List<GetBoardMiniRes>>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @GetMapping("deleted")
    public ResultDto<List<GetBoardMiniRes>> getBoardDeletedList(@RequestParam(name = "user_id") Long signedUserId) {
        HttpStatus code = HttpStatus.OK;
        String msg = "삭제된 board 불러오기 완료요~!~!";
        List<GetBoardMiniRes> result = null;

        try {
            result = service.getBoardDeletedList(signedUserId);
        } catch (Exception e) {
            code = HttpStatus.NOT_ACCEPTABLE;
            msg = e.getMessage();
        }

        return ResultDto.<List<GetBoardMiniRes>>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @GetMapping("todo")
    public ResultDto<TodoListRes> getBoardTodoList(@RequestParam(name = "user_id") Long signedUserId) {


        HttpStatus code = HttpStatus.OK;
        String msg = "todo 리스트 불러오기 완료요~!~!";
        TodoListRes result = null;

        try {
            result = service.getBoardTodoList(signedUserId);
        } catch (Exception e) {
            code = HttpStatus.NOT_ACCEPTABLE;
            msg = e.getMessage();
        }

        return ResultDto.<TodoListRes>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @PutMapping
    public ResultDto<Integer> updateBoard(@RequestBody UpdateBoardReq p) {
        HttpStatus code = HttpStatus.OK;
        String msg = "보드 수정 완료~!~!";
        int result = -1;

        try {
            result = service.updateBoard(p);
        } catch (Exception e) {
            code = HttpStatus.NOT_ACCEPTABLE;
            msg = e.getMessage();
        }
        if (result == 0) {
            msg = "보드가 수정된게 없는데용?";
        }

        return ResultDto.<Integer>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @PatchMapping("state")
    public ResultDto<Integer> updateBoardState(@RequestBody UpdateBoardStateReq p) {
        HttpStatus code = HttpStatus.OK;
        String msg = "보드 상태 업데이트 완료~!~!";
        int result = -1;

        try {
            result = service.updateBoardState(p);
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

    @PatchMapping("dnd")
    public ResultDto<Integer> updateBoardDnD(@RequestBody UpdateBoardDnDReq p) {
        HttpStatus code = HttpStatus.OK;
        String msg = "보드 DnD 업데이트 완료~!~!";
        int result = -1;

        try {
            result = service.updateBoardDnD(p);
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
    public ResultDto<Integer> deleteBoard(@RequestBody List<DeleteBoardReq> p) {
        HttpStatus code = HttpStatus.OK;
        String msg = "보드 삭제 완료~!~!";
        int result = -1;

        try {
            result = service.deleteBoard(p);
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

    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////

    @DeleteMapping("file")
    public ResultDto<Integer> deleteFile(@RequestBody DeleteFileReq p) {
        HttpStatus code = HttpStatus.OK;
        String msg = "파일 삭제 완료~!~!";
        int result = -1;

        try {
            result = service.deleteFile(p);
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

    @PostMapping("file")
    public ResultDto<FileRes> createFile(@RequestPart MultipartFile file, @RequestPart CreateFileReq p) {
        HttpStatus code = HttpStatus.OK;
        String msg = "파일 생성 완료~!~!";
        FileRes result = null;

        try {
            result = service.createFile(file, p);
        } catch (Exception e) {
            code = HttpStatus.NOT_ACCEPTABLE;
            msg = e.getMessage();
        }

        return ResultDto.<FileRes>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

}
