package com.green.todo.user;


import com.green.todo.Mail.MailService;
import com.green.todo.user.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final String ID_PATTERN = "^[a-zA-Z0-9]{6,12}$";
    private final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d|.*[!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?]).{8,20}$";
    private final Pattern idPattern = Pattern.compile(ID_PATTERN);
    private final Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
    private final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
    private final MailService mailService;


    public int postSignUp(SignUpPostReq p) {

        if (!validateEmail(p.getEmail())) {
            throw new IllegalArgumentException("이메일 형식에 맞게 작성하여 주십시오");
        } else if (!validateId(p.getId())) {
            throw new IllegalArgumentException("아이디는 영문자와 숫자로만 구성되어야 하며, 길이는 6자 이상 12자 이하여야 합니다.");
        } else if (!validatePassword(p.getPwd())) {
            throw new IllegalArgumentException("비밀번호는 대/소문자, 숫자, 특수문자를 포함하여 8자 이상 20자 이하여야 합니다.");
        } else if (isDuplicatedId(p.getId())) {
            throw new IllegalArgumentException("이미 사용중인 아이디입니다");
        }
        String hashedPw = BCrypt.hashpw(p.getPwd(), BCrypt.gensalt());
        p.setPwd(hashedPw);


        int result = mapper.postUser(p);
        if (result == 0) {
            throw new RuntimeException("tqt");
        }
        return result;
    }

    public SignInRes postSignIn(SignInPostReq p) {
        User user = mapper.getUserById(p.getId());
        if (user == null || !BCrypt.checkpw(p.getPwd(), user.getPwd())) {
            throw new RuntimeException("아이디와 비밀번호를 확인해주세요");
        }

        return SignInRes.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public int deleteUser(DelUserReq p) {
        log.info("p의 유저ID{}", p.getSignedUserId());
        User user = mapper.getUserByUserId(p.getSignedUserId());
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다");
        }
        log.info("User의 유저 ID{}", user.getUserId());
        int result = mapper.delUser(p.getSignedUserId());
        return result;
    }

    // 비번 재설정
    public int patchPassWord(ChangePasswordPatchReq p) {
        if (!validatePassword(p.getNewPw())) {
            throw new IllegalArgumentException("비밀번호는 대소문자, 숫자, 특수문자를 포함하여 8자 이상 20자 이하여야 합니다.");
        }

        String hashPassWord = BCrypt.hashpw(p.getNewPw(), BCrypt.gensalt());
        p.setNewPw(hashPassWord);

        int result = mapper.patchPassword(p.getUserId());
        return result;

    }

    public String findId(FindIdReq p) {
        String result = null;
        try {
            result = mapper.findId(p);
        } catch (Exception e) {
            throw new RuntimeException("id 찾기 쿼리이슈");
        }
        if (result == null) {
            throw new IllegalArgumentException("존재하지 않는 사용자이거나 이메일");
        }

        return result;
    }

    public PwdAcRes reqPwd(PwdAcReq p) {
        PwdAcRes result = mapper.reqPwd(p);
        if (!validateEmail(p.getEmail())) {
            throw new IllegalArgumentException("이메일은 지메일로~");
        }
        if (result == null) {
            throw new IllegalArgumentException("코드가 틀렸습니다");
        }
        User user = mapper.getUserById(p.getId());
        if (!user.getEmail().equals(p.getEmail())) {
            throw new RuntimeException("이메일이 다릅니다.");
        }
        try {
            String secretCode = mailService.sendSimpleMessage(p.getEmail());
            result.setCode(secretCode);
        } catch (Exception e) {
            throw new RuntimeException("메일 전송 실패");
        }

        return result;
    }

    public long updUser(EditReq p) {

        if (p.getUserId() == null) {
            throw new IllegalArgumentException("유저정보가 일치하지 않습니다");
        }
        if (p.getEmail() != null && !validateEmail(p.getEmail())) {
            throw new IllegalArgumentException("이메일은 지메일로~");
        }
        if (p.getPwd() == null) {
            throw new RuntimeException("기존 비밀번호는 필수로 넣어야 합니다.");
        }
        if (!validatePassword(p.getPwd())) {
            throw new IllegalArgumentException("비밀번호는 대소문자, 숫자, 특수문자를 포함하여 8자 이상 20자 이하여야 합니다.");
        }
        if (p.getNewPw() != null && !validatePassword(p.getNewPw())) {
            throw new IllegalArgumentException("비밀번호는 대소문자, 숫자, 특수문자를 포함하여 8자 이상 20자 이하여야 합니다.");
        }

        User user = mapper.getUserByUserId(p.getUserId());

        if (!BCrypt.checkpw(p.getPwd(), user.getPwd())) {
            throw new RuntimeException("비밀번호를 확인해주세요");
        }

        if(p.getNewPw() != null) {
            String hashPassWord = BCrypt.hashpw(p.getNewPw(), BCrypt.gensalt());
            p.setNewPw(hashPassWord);
        }
//        log.info("{}, {}",p.getNewPw(), p.getPwd());
        int res = mapper.updUser(p);
        if (res != 1) {
            throw new RuntimeException("업데이트 실패");
        }

        return p.getUserId();
    }

    private boolean validateId(String id) {
        return idPattern.matcher(id).matches();
    }

    private boolean validatePassword(String password) {
        return passwordPattern.matcher(password).matches();
    }

    private boolean isDuplicatedId(String id) {
        User user = mapper.getUserById(id);
        return user != null;
    }

    private boolean validateEmail(String email) {
        return emailPattern.matcher(email).matches();
    }
}
