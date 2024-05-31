package com.green.todo.board;

import com.green.todo.board.model.req.CreateBoardFileDto;
import com.green.todo.board.model.req.CreateBoardReq;
import com.green.todo.board.model.res.GetBoardRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    int createBoard(CreateBoardReq p);
    void createBoardFiles(CreateBoardFileDto dto);
    List<GetBoardRes> getBoardListByMonth(int month);
    List<String> getBoardFiles(long boardId);
}
