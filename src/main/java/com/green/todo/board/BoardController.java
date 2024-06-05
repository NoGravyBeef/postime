package com.green.todo.board;

import com.green.todo.board.model.req.*;
import com.green.todo.board.model.res.GetBoardRes;
import com.green.todo.board.model.res.FileRes;
import com.green.todo.common.model.ResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
//                                       ,@RequestPart List<Tag> tags
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
    public ResultDto<List<GetBoardRes>> getBoardListByMonth(@RequestParam(name = "user_id") Long userId, @RequestParam Integer month) {


        HttpStatus code = HttpStatus.OK;
        String msg = "board 불러오기 완료요~!~!";
        List<GetBoardRes> result = null;

        try {

            result = service.getBoardList(userId, month);
        } catch (Exception e) {
            code = HttpStatus.NOT_ACCEPTABLE;
            msg = e.getMessage();
        }

        return ResultDto.<List<GetBoardRes>>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @GetMapping("todo")
    public ResultDto<List<GetBoardRes>> getBoardTodoListByMonth(@RequestParam(name = "user_id") Long userId, @RequestParam Integer month) {


        HttpStatus code = HttpStatus.OK;
        String msg = "board 불러오기 완료요~!~!";
        List<GetBoardRes> result = null;

        try {

            result = service.getBoardList(userId, month);
        } catch (Exception e) {
            code = HttpStatus.NOT_ACCEPTABLE;
            msg = e.getMessage();
        }

        return ResultDto.<List<GetBoardRes>>builder()
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
