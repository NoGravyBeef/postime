package com.green.todo.user.model;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;

@Getter
@Setter
public class DelUserReq {
    @Schema(example = "3", description = "유저 PK")
    @Parameter(name = "signed_user_id")
    private String signedUserId;

    @ConstructorProperties({"signed_user_id"})
    public DelUserReq(String signedUserId) {
        this.signedUserId = signedUserId;
    }
}
