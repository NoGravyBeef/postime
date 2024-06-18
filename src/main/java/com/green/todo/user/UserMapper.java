package com.green.todo.user;

import com.green.todo.user.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper

public interface UserMapper {
    int postUser(SignUpPostReq p);
    User getUserById(String p);
    User getUser(ChangePasswordPatchReq p);
    int delUser(long p);
    User getUserByUserId(long userId);
    int patchPassword(long userId, String newPw);
    String findId(FindIdReq p);
    int updUser(EditReq p);
    PwdAcRes reqPwd(PwdAcReq p);
    Long checkUser(CheckReq p);
    Long checkPassword(CheckPassword p);
}
