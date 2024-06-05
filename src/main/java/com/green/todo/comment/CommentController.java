package com.green.todo.comment;

import com.green.todo.comment.model.req.CommentDeleteReq;
import com.green.todo.comment.model.req.CommentPostReq;
import com.green.todo.comment.model.req.CommentUpdateReq;
import com.green.todo.comment.model.res.CommentGetRes;
import com.green.todo.common.CommonUtils;
import com.green.todo.common.model.ResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.green.todo.common.model.ResultDto.resultDto;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/board/comment")
@Tag(name = "댓글 컨트롤러")
public class CommentController {
    private final CommentService service;
    private final CommonUtils utils;

    @PostMapping
    @Operation(summary = "댓글 작성", description = "댓글 PK 리턴")
    public ResultDto<Long> postComment(@RequestBody CommentPostReq p) {
        utils.init("댓글 작성을 완료하였습니다.");
        long result = -1;
        try {
            result = service.postComment(p);
        } catch (Exception e) {
            utils.noAcceptable(e);
        }
        return resultDto(utils.getCode(), utils.getMsg(),result);
    }

    @GetMapping
    @Operation(summary = "댓글 리스트 불러오기", description = "댓글 리스트 리턴")
    public ResultDto<List<CommentGetRes>> getCommentList(@RequestParam("board_id") Long boardId) {
        utils.init("댓글 불러오기 완료");
        List<CommentGetRes> result = new ArrayList<>(0);
        try {
            result = service.getCommentList(boardId);
        } catch (Exception e) {
            utils.noAcceptable(e);
        }
        return resultDto(utils.getCode(), utils.getMsg(),result);
    }

    @PutMapping
    @Operation(summary = "댓글 수정", description = "수정된 댓글 내용 리턴")
    public ResultDto<String> updateComment (@RequestBody CommentUpdateReq p) {
        utils.init("댓글 수정을 완료하였습니다.");
        String result = null;
        try {
            result = service.updateComment(p);
        } catch (Exception e) {
            utils.noAcceptable(e);
        }
        return resultDto(utils.getCode(), utils.getMsg(),result);
    }

    @DeleteMapping
    @Operation(summary = "댓글 삭제", description = "삭제되면 1을 리턴")
    public ResultDto<Integer> deleteComment (@ParameterObject @ModelAttribute CommentDeleteReq p) {
        utils.init("댓글 삭제를 완료하였습니다.");
        int result = -1;
        try {
            result = service.deleteComment(p);
        } catch (Exception e) {
            utils.noAcceptable(e);
        }
        return resultDto(utils.getCode(), utils.getMsg(),result);
    }

}
