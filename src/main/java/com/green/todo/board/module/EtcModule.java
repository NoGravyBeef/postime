package com.green.todo.board.module;

import com.green.todo.board.model.req.CreateBoardReq;
import com.green.todo.common.model.GlobalConst;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EtcModule {
    public static void validateCreateBoardReq(CreateBoardReq p) throws Exception{
        if (p.getTitle() == null) throw new Exception("title 이 null 인데용?");
        if (p.getContent() == null) throw new Exception("content 가 null 인데용?");


        if (p.getColor() <= 0 || p.getColor() > GlobalConst.countOfCalendar) throw new Exception("올바른 순위값 넣어.");
        if (p.getState() <= 0 || p.getState() > 3) throw new Exception("올바른 상태값 넣어.");
    }
}
