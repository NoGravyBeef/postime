package com.green.todo.board.module;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RequiredArgsConstructor
public class DateTimeValidateUtils {

    public static LocalDate dateValidator (String date) throws Exception{
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate Date;
        try {
            Date = LocalDate.parse(date, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new Exception("날짜 형식이 올바르지 않습니다.");
        }

        return Date;
    }

    public static LocalTime timeValidator (String time) throws Exception{
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        LocalTime Time;
        try {
            Time = LocalTime.parse(time, timeFormatter);
        } catch (DateTimeParseException e) {
            throw new Exception("시간 형식이 올바르지 않습니다.");
        }

        return Time;
    }

}
