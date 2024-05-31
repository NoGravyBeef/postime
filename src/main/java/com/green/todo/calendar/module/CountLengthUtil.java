package com.green.todo.calendar.module;

public class CountLengthUtil {

    public static int countLength(String string) {
        int length = 0;
        for (int i = 0; i < string.length(); i++) {
            char ch = string.charAt(i);
            // 한글인지 확인
            if (ch >= '\uAC00' && ch <= '\uD7A3') {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length;
    }

}
