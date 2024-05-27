package com.green.todo.user;

import com.green.todo.user.model.UserEntity;
import com.green.todo.user.model.UserSignInReq;
import com.green.todo.user.model.UserSignUpReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int userSignUp(UserSignUpReq p);
    UserEntity getUserByUserId(String user_id);
}
