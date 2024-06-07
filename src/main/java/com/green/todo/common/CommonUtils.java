package com.green.todo.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Getter
@Setter
public class CommonUtils {

    private int code;
    private String msg;

    public void init(String msg){
        this.code = 2;
        this.msg = msg;
    }
    public void noAcceptable(Exception e) {
        this.msg = e.getMessage();
        this.code = 4;
    }

    public boolean isWithinByteLimit(String str, int limitByte) {
        return str.getBytes(StandardCharsets.UTF_8).length <= limitByte;
    }
}
