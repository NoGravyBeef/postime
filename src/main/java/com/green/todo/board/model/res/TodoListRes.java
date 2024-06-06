package com.green.todo.board.model.res;

import lombok.Data;

import java.util.List;

@Data
public class TodoListRes {
    private List<GetBoardTodoRes> untilTodayBoard;
    private List<GetBoardTodoRes> untilThisMonthBoard;
    private List<GetBoardTodoRes> untilNextMonthBoard;
}
