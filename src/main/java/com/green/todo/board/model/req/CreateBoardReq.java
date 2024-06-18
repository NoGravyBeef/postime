package com.green.todo.board.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.green.todo.tag.model.req.TagPostReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class CreateBoardReq {
    @JsonIgnore
    private long boardId;
    @Schema(example = "1", description = "현재 들어가있는, 캘린더의 id")
    private Long calendarId;
    @Schema(example = "1", description = "로그인 중인 user_id 보내주기")
    private Long signedUserId;
    @Schema(example = "title", description = "글 제목")
    private String title;
    @Schema(example = "content", description = "글 내용")
    private String content;
//    private Integer state;
    @Schema(example = "2024-04-11", description = "일정 시작 날짜")
    private String startDay;
    @Schema(example = "2024-04-12", description = "일정 마감 날짜")
    @JsonProperty("dDay")
    private String dDay;
    @Schema(example = "12:30:00", description = "일정 마감 시간")
    private String deadLine;
    @Schema(example = "[1, 2]", description = "재 사용할 태그의 id들")
    private List<Long> existTag = new ArrayList<>();
    private List<TagPostReq> notExistTag = new ArrayList<>();
}
