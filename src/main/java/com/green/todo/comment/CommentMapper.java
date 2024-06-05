package com.green.todo.comment;

import com.green.todo.comment.model.req.CommentDeleteReq;
import com.green.todo.comment.model.req.CommentPostReq;
import com.green.todo.comment.model.req.CommentUpdateReq;
import com.green.todo.comment.model.res.CommentGetRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    int insComment(CommentPostReq p);
    List<CommentGetRes> getCommentList(long boardId);
    CommentGetRes getCommentOne (long commentId);
    int updateComment (CommentUpdateReq p);
    int deleteComment(CommentDeleteReq p);
}
