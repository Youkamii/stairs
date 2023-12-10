package com.sparta.stairs.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponseDto {

    private final String msg;
    private final int status;

    public CommonResponseDto(String msg, int status) {
        this.msg = msg;
        this.status = status;
    }
}

