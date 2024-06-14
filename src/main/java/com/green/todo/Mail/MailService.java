package com.green.todo.Mail;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

 // 추가

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    private String authNum;



    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, to); // 보내는 대상
        message.setSubject("PostTime 이메일 인증"); // 제목

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 인증코드</h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 창으로 돌아가 입력해주세요</p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'> 인증코드</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += authNum + "</strong>"; // 메일 인증번호
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");
        message.setFrom(new InternetAddress("postime9@gmail.com", "PosTime"));

        return message;
    }

    public String createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(3);  // 수정: 인덱스 범위 수정

            switch (index) {
                case 0 -> key.append((char) (random.nextInt(26) + 97));  // 소문자
                case 1 -> key.append((char) (random.nextInt(26) + 65));  // 대문자
                case 2 -> key.append(random.nextInt(10));  // 숫자
            }
        }
        return key.toString();
    }

    public String sendSimpleMessage(String sendEmail) throws Exception {

        authNum = createCode();

        MimeMessage message = createMessage(sendEmail); // 메일 발송
        javaMailSender.send(message);

        return authNum;
    }
}
