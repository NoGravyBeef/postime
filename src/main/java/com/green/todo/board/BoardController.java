package com.green.todo.board;

import com.green.todo.board.model.req.CreateBoardReq;
import com.green.todo.board.model.res.GetBoardRes;
import com.green.todo.common.model.ResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
        String msg = "성공";

        long result;

        try {
            result = service.createBoard(p, files);
        } catch (Exception e) {
            code = HttpStatus.NO_CONTENT;
            msg = e.getMessage();
            result = -1;
        }

        return ResultDto.<Long>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @GetMapping
    public ResultDto<List<GetBoardRes>> getBoardList(@RequestParam String month) {
        //TODO: 각 board마다 file select또한 추가해야함.

        HttpStatus code = HttpStatus.OK;
        String msg = "성공";

        List<GetBoardRes> result = new ArrayList<>();

        try {
            int integerMonth = Integer.parseInt(month);
            result = service.getBoardList(integerMonth);
        } catch (Exception e) {
            code = HttpStatus.NO_CONTENT;
            msg = e.getMessage();
        }

        return ResultDto.<List<GetBoardRes>>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

//    @PutMapping
//    public ResultDto<>
}
