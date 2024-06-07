package com.green.todo.board;

import com.green.todo.board.model.req.*;
import com.green.todo.board.model.res.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    int createBoard(CreateBoardReq p);
    void createBoardFiles(CreateBoardFileDto dto);
    GetBoardRes getBoardInfoByBoardId(Long boardId);
    List<GetBoardMiniRes> getBoardMiniListByUserIdAndMonth(Long userId, Integer month);
    List<FileRes> getBoardFiles(long boardId);
    BoardEntity getBoardByBoardId(Long boardId);
    List<GetBoardMiniRes> getBoardMiniByState(Long userId, Integer state);
    List<GetBoardMiniRes> getBoardSearchList(String searchWord, Long signedUserId);
    List<GetBoardTodoRes> selectBoardsByUserIdForToday(Long userId);
    List<GetBoardTodoRes> selectBoardsByUserIdForCurrentMonth(Long userId);
    List<GetBoardTodoRes> selectBoardsByUserIdForNextMonth(Long userId);
    int updateBoard(UpdateBoardReq p);
    int updateBoardState(UpdateBoardStateReq p);
    int updateBoardDnD(UpdateBoardDnDReq p);
    int deleteBoard(DeleteBoardReq p);
    int deleteFile(DeleteFileReq p);
    int createFile(CreateFileReq p);
}
