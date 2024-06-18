package com.green.todo.board.module;

import com.green.todo.board.model.req.CreateBoardReq;
import com.green.todo.common.model.GlobalConst;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EtcModule {
    public static void validateCreateBoardReq(CreateBoardReq p) throws Exception{
        if (p.getTitle() == null) throw new Exception("title 이 입력되지 않았습니다.");
        if (p.getContent() == null) throw new Exception("content 가 입력되지 않았습니다.");
//        if (p.getState() <= 0 || p.getState() > 3) throw new Exception("올바른 상태값 넣어.");
    }
}
