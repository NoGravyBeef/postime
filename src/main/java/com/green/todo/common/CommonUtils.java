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

    private HttpStatus code;
    private String msg;

    public void init(String msg){
        this.code = HttpStatus.OK;
        this.msg = msg;
    }
    public void noAcceptable(Exception e) {
        this.msg = e.getMessage();
        this.code = HttpStatus.NOT_ACCEPTABLE;
    }

    public boolean isWithinByteLimit(String str, int limitByte) {
        return str.getBytes(StandardCharsets.UTF_8).length <= limitByte;
    }
}
