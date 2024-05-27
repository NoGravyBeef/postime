package com.green.todo.user;

import com.green.todo.common.model.ResultDto;
import com.green.todo.user.model.UserEntity;
import com.green.todo.user.model.UserSignInReq;
import com.green.todo.user.model.UserSignInRes;
import com.green.todo.user.model.UserSignUpReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    HttpStatus code;
    String msg;

    @PostMapping("sign-up")
    @Operation(summary = "회원가입", description = "<p>회원가입하는 공간입니다.</p>" +
            "<p>비밀번호는 정규표현식 쓰셔야 하무니다.</p>")
    @ApiResponse(responseCode = "200",description =
            "<p>statusCode = 200 => 정상, 204 => 회원가입 실패 및 오류 </p>"+
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData =  (-1 => 아이디 중복),  (-2 => 비밀번호 양식 오류),  (1 => 회원가입 성공) </p>"
    )
    public ResultDto<Integer> userSignUp(@RequestBody UserSignUpReq p) {
        int result = service.userSignUp(p);

        if (result == -1) {
            code = HttpStatus.NO_CONTENT;
            msg = "아이디가 이미 존재합니다.";
        } else if (result == -2) {
            code = HttpStatus.NO_CONTENT;
            msg = "비밀번호 양식이 올바르지 않습니다.";
        } else if (result == 1) {
            code = HttpStatus.OK;
            msg = "회원가입 성공";
        } else {
            code = HttpStatus.NO_CONTENT;
            msg = "그 외의 문제";
        }

        return ResultDto.<Integer>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @PostMapping("sign-in")
    @Operation(summary = "로그인", description = "로그인하는 공간입니다.")
    @ApiResponse(responseCode = "200",description =
                    "<p>statusCode = 200 => 정상, 204 => 로그인 실패 및 오류 </p>"+
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = userIndex 및 userName 반환 </p>"
    )
    public ResultDto<UserSignInRes> userSignIn(@RequestBody UserSignInReq p) {
        UserSignInRes result = null;

        UserEntity userEntity = service.getUserEntity(p.getUserId());

        if (!service.ValidationUserId(userEntity) || !service.ValidationUserPw(p.getUserPw())) {
            code = HttpStatus.NO_CONTENT;
            msg = "아이디 또는 비밀번호가 옳지 않습니다.";
            result = UserSignInRes.builder()
                    .userName("")
                    .userIndex(-1)
                    .build();
        } else if (service.matchSignInInfo(userEntity, p.getUserPw())) {
            code = HttpStatus.OK;
            msg = "로그인 성공";
            result = UserSignInRes.builder()
                    .userIndex(userEntity.getUserIndex())
                    .userName(userEntity.getUserName())
                    .build();
        }

        return ResultDto.<UserSignInRes>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();


    }
}
