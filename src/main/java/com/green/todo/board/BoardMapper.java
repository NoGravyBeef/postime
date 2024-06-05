package com.green.todo.board;

import com.green.todo.board.model.req.*;
import com.green.todo.board.model.res.BoardEntity;
import com.green.todo.board.model.res.GetBoardRes;
import com.green.todo.board.model.res.FileRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    int createBoard(CreateBoardReq p);
    void createBoardFiles(CreateBoardFileDto dto);
    List<GetBoardRes> getBoardListByUserIdAndMonth(Long userId, Integer month);
    List<FileRes> getBoardFiles(long boardId);
    BoardEntity getBoardByBoardId(Long boardId);
    int updateBoard(UpdateBoardReq p);
    int updateBoardState(UpdateBoardStateReq p);
    int updateBoardDnD(UpdateBoardDnDReq p);
    int deleteBoard(DeleteBoardReq p);
    int deleteFile(DeleteFileReq p);
    int createFile(CreateFileReq p);
}
