package com.green.todo.user;

import com.green.todo.common.model.ResultDto;
import com.green.todo.user.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.hibernate.annotations.Check;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "유저 CRUD")
public class UserController {
    private final UserService service;


    @PostMapping
    @Operation(summary = "회원가입", description = "리턴값은 영향 받은 행" +
            "/모든 데이터 필수 기입")
    @ApiResponse(responseCode = "200", description =
            "<p>statusCode = 2 => 정상</p>" +
                    "<p>statusCode = 4 => 오류난거임~!~! </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 영향받은 행~!~! </p>"
    )
    public ResultDto<Integer> postUser(@RequestBody SignUpPostReq p) {
        int code = 2;
        String msg = "회원가입 성공";
        int result = 0;
        try {
            result = service.postSignUp(p);
        } catch (Exception e) {
            code = 4;
            msg = e.getMessage();

        }

        return ResultDto.<Integer>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @PostMapping("sign-in")
    @Operation(summary = "로그인", description = "리턴값은 유저 PK" +
            "/이름/이메일")
    @ApiResponse(responseCode = "200", description =
            "<p>statusCode = 2 => 정상</p>" +
                    "<p>statusCode = 4 => 오류난거임~!~! </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 유저 PK~!~! </p>"
    )
    public ResultDto<SignInRes> postSignIn(@RequestBody SignInPostReq p) {
        int code = 2;
        String msg = "로그인 성공";
        SignInRes result = null;

        try {
            result = service.postSignIn(p);
        } catch (Exception e) {
            code = 4;
            msg = e.getMessage();
        }

        return ResultDto.<SignInRes>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @DeleteMapping
    @Operation(summary = "회원탈퇴", description = "<p>리턴값은 영향받은 행</p>" +
            "<p>모든 데이터 필수 기입</p>")
    @ApiResponse(responseCode = "200", description =
            "<p>statusCode = 2 => 정상</p>" +
                    "<p>statusCode = 4 => 오류난거임~!~! </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 영향받은 행~!~! </p>"
    )
    public ResultDto<Integer> delUser(@ParameterObject @ModelAttribute DelUserReq p) {
        log.info("p유저 Id {}", p.getSignedUserId());
        int code = 2;
        String msg = "탈퇴 성공";
        int result = 0;
        try {
            result = service.deleteUser(p);
        } catch (Exception e) {
            code = 4;
            msg = e.getMessage();
        }
        return ResultDto.<Integer>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @GetMapping("resetpwd")
    @Operation(summary = "비번찾기", description = "리턴값은 코드와 유저 PK")
    @ApiResponse(responseCode = "200", description =
            "<p>statusCode = 2 => 정상</p>" +
                    "<p>statusCode = 4 => 오류난거임~!~! </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 인증 코드와 유저 PK~!~! </p>"
    )
    public ResultDto<PwdAcRes> rePwd(@ParameterObject PwdAcReq p) {
        int code = 2;
        String msg = "이메일 전송완료 ";
        PwdAcRes result = null;
        try {
            result = service.reqPwd(p);
        } catch (Exception e) {
            code = 4;
            msg = e.getMessage();
        }
        return ResultDto.<PwdAcRes>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @PatchMapping
    @Operation(summary = "비번 재설정", description = "리턴값은 영향 받은 행" +
            "/모든 데이터 필수 기입")
    @ApiResponse(responseCode = "200", description =
            "<p>statusCode = 2 => 정상</p>" +
                    "<p>statusCode = 4 => 오류난거임~!~! </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 영향받은 행~!~! </p>"
    )
    public ResultDto<Integer> patchPassword(@RequestBody ChangePasswordPatchReq p) {
        int code = 2;
        String msg = "재설정 완료";
        int result = 0;
        try {
            result = service.patchPassWord(p);
        } catch (Exception e) {
            code = 4;
            msg = e.getMessage();
        }
        return ResultDto.<Integer>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @GetMapping
    @Operation(summary = "아이디 찾기", description = "리턴 값은 유저의 아이디")
    @ApiResponse(responseCode = "200", description =
            "<p>statusCode = 2 => 정상</p>" +
                    "<p>statusCode = 4 => 오류난거임~!~! </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 유저 ID~!~! </p>"
    )
    public ResultDto<String> findId(@ParameterObject @ModelAttribute FindIdReq p) {
        int code = 2;
        String msg = "아이디 찾기완료";
        String result = null;
        try {
            result = service.findId(p);
        } catch (Exception e) {
            code = 4;
            msg = e.getMessage();
        }
        return ResultDto.<String>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @PutMapping
    @Operation(summary = "회원정보수정", description = "리턴값은 유저 PK/userPwd는 필수"
            + "나머지는 사용자 선택")
    @ApiResponse(responseCode = "200", description =
            "<p>statusCode = 2 => 정상</p>" +
                    "<p>statusCode = 4 => 오류난거임~!~! </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 유저 PK~!~! </p>"
    )
    public ResultDto<Long> updUser(@RequestBody EditReq p) {
        int code = 2;
        String msg = "정보수정완료";
        long result = 0;
        try {
            result = service.updUser(p);
        } catch (Exception e) {
            code = 4;
            msg = e.getMessage();
        }
        return ResultDto.<Long>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @GetMapping("checkuser")
    @Operation(summary = "중복 체크", description = "리턴 값은 참 또는 거짓")
    @ApiResponse(responseCode = "200", description =
            "<p>statusCode = 2 => 정상</p>" +
                    "<p>statusCode = 4 => 오류난거임~!~! </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 참, 거짓 </p>"
    )
    public ResultDto<Boolean> checkUser(@ParameterObject @ModelAttribute CheckReq p) {
        int code = 2;
        String msg = "중복체크완료";
        Boolean result = false;
        try {
            result = service.checkUser(p);
        } catch (Exception e) {
            code = 4;
            msg = e.getMessage();
        }
        return ResultDto.<Boolean>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }

    @PostMapping("checkPwd")
    @Operation(summary = "비번 검증", description = "리턴 값은 참 또는 거짓")
    @ApiResponse(responseCode = "200", description =
            "<p>statusCode = 2 => 정상</p>" +
                    "<p>statusCode = 4 => 오류난거임~!~! </p>" +
                    "<p>resultMsg = 해당하는 코드의 자세한 정보 </p>" +
                    "<p>resultData = 참, 거짓 </p>")
    public ResultDto<String> checkPassword(@RequestBody CheckPassword p) {
        int code = 2;
        String msg = "검증완료";
        String result = null;
        try {
            result = service.checkPwd(p);
        } catch (Exception e) {
            code = 4;
            msg = e.getMessage();
        }
        return ResultDto.<String>builder()
                .statusCode(code)
                .resultMsg(msg)
                .resultData(result)
                .build();
    }
}
