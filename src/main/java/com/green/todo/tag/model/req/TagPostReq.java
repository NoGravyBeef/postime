package com.green.todo.tag.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.BindParam;

@Getter
@Setter
public class TagPostReq {
    @Schema(example = "2", description = "태그가 생성되는 캘린더의 PK")
    private Long calendarId;
    @Schema(example = "프로젝트", description = "태그 이름")
    private String title;
    @Schema(example = "1", description = "태그 색상")
    private Integer color;
    @JsonIgnore
    private long tagId;

}
