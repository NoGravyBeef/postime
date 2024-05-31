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
            throw new Exception("날짜 똑바로 입력해~");
        }

        return Date;
    }

    public static void timeValidator (String time) throws Exception{
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        LocalTime Time;
        try {
            Time = LocalTime.parse(time, timeFormatter);
        } catch (DateTimeParseException e) {
            throw new Exception("시간 똑바로 입력해~");
        }
    }

}
