package com.green.todo.comment;

import com.green.todo.comment.model.req.CommentDeleteReq;
import com.green.todo.comment.model.req.CommentPostReq;
import com.green.todo.comment.model.req.CommentUpdateReq;
import com.green.todo.comment.model.res.CommentGetRes;
import com.green.todo.common.CommonUtils;
import com.green.todo.common.model.ResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/board/comment")
@Tag(name = "댓글 컨트롤러")
public class CommentController {
    private final CommentService service;
    private final CommonUtils utils;

    @PostMapping
    @Operation(summary = "댓글 작성", description = "<strong>모두 필수 입력입니다.</strong>" +
            "<p>성공시 댓글 PK, 실패시 -1을 리턴합니다.</p>")
    @ApiResponse(responseCode = "200",description =
            "<p>statusCode = 2 => 정상</p>"+
                    "<p>statusCode = 4 => 오류난거임~!~! </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 댓글 PK~!~! </p>"
    )
    public ResultDto<Long> postComment(@RequestBody CommentPostReq p) {
        utils.init("댓글 작성을 완료하였습니다.");
        long result = -1;
        try {
            result = service.postComment(p);
        } catch (Exception e) {
            utils.noAcceptable(e);
        }
        return new ResultDto<>(utils.getCode(), utils.getMsg(),result);
    }

    @GetMapping
    @Operation(summary = "댓글 리스트 불러오기", description = "<strong>필수 입력입니다.</strong>" +
            "<p>댓글 리스트 리턴합니다.</p>")
    @ApiResponse(responseCode = "200",description =
            "<p>statusCode = 2 => 정상</p>"+
                    "<p>statusCode = 4 => 오류난거임~!~! </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 가지고온 댓글 목록~!~! </p>"
    )
    public ResultDto<List<CommentGetRes>> getCommentList(@RequestParam("board_id") String boardId) {
        utils.init("댓글 불러오기 완료");
        List<CommentGetRes> result = new ArrayList<>(0);
        try {
            result = service.getCommentList(boardId);
        } catch (Exception e) {
            utils.noAcceptable(e);
        }
        return new ResultDto<>(utils.getCode(), utils.getMsg(),result);
    }

    @PutMapping
    @Operation(summary = "댓글 수정하기", description = "<strong>모두 필수 입력입니다.</strong>" +
            "<p>수정된 댓글의 내용을 리턴합니다.</p>")
    @ApiResponse(responseCode = "200",description =
            "<p>statusCode = 2 => 정상</p>"+
                    "<p>statusCode = 4 => 오류난거임~!~! </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 수정된 댓글 내용~!~! </p>"
    )
    public ResultDto<String> updateComment (@RequestBody CommentUpdateReq p) {
        utils.init("댓글 수정을 완료하였습니다.");
        String result = null;
        try {
            result = service.updateComment(p);
        } catch (Exception e) {
            utils.noAcceptable(e);
        }
        return new ResultDto<>(utils.getCode(), utils.getMsg(),result);
    }

    @DeleteMapping
    @Operation(summary = "댓글 삭제하기", description = "<strong>모두 필수 입력입니다!</strong>" +
            "<p>삭제되면 1을 리턴 실패하면 -1을 리턴합니다.</p>")
    @ApiResponse(responseCode = "200",description =
            "<p>statusCode = 2 => 정상</p>"+
                    "<p>statusCode = 4 => 오류난거임~!~! </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 삭제 성공하면 1~!~! </p>"
    )
    public ResultDto<Integer> deleteComment (@ParameterObject @ModelAttribute CommentDeleteReq p) {
        utils.init("댓글 삭제를 완료하였습니다.");
        int result = -1;
        try {
            result = service.deleteComment(p);
        } catch (Exception e) {
            utils.noAcceptable(e);
        }
        return new ResultDto<>(utils.getCode(), utils.getMsg(),result);
    }

}
