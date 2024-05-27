package com.green.todo.user;

import com.green.todo.user.model.UserEntity;
import com.green.todo.user.model.UserSignInReq;
import com.green.todo.user.model.UserSignInRes;
import com.green.todo.user.model.UserSignUpReq;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final String PASSWORD_PATTERN =
            "^(?=.*[A-Z])(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$";
    private final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

/*--------------------------아래부터 회원가입----------------------------*/
    @Transactional
    public int userSignUp(UserSignUpReq p){
        int result;

        UserEntity userEntity = mapper.getUserByUserId(p.getUserId());
        if (userEntity != null) {
            return result = -1;
        }

        Matcher matcher = pattern.matcher(p.getUserPw());
        if(!matcher.matches()) {
            return result = -2;
        }

        String newPw = BCrypt.hashpw(p.getUserPw(), BCrypt.gensalt());
        p.setUserPw(newPw);

        result = mapper.userSignUp(p);

        return result;
    }

/*--------------------------아래부터 로그인----------------------------*/

    public UserEntity getUserEntity(String userId) {
        return mapper.getUserByUserId(userId);
    }

    public boolean ValidationUserId(UserEntity userEntity) {
        if (userEntity != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ValidationUserPw(String userPw) {
        Matcher matcher = pattern.matcher(userPw);
        return matcher.matches();
    }

    public boolean matchSignInInfo(UserEntity userEntity, String userPw) {
        return BCrypt.checkpw(userPw, userEntity.getUserPw());
    }
}
