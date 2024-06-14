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

    public boolean validateColor(String color) {
        if (color.equals("#FF922B") || color.equals("#FCC419") || color.equals("#51CF66") || color.equals("#339AF0") ||
                color.equals("#845EF7") || color.equals("#CC5DE8") || color.equals("#F06595") || color.equals("#FF6B6B") || color.equals("#ABD5BD")) {
            return true;
        } else {
            return false;
        }
    }
}
