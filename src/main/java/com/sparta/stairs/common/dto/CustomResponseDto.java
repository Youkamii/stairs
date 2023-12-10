package com.sparta.stairs.common.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class CustomResponseDto {
    private final String message;
    private final int statusCode;

    @Builder
    public CustomResponseDto(String message, HttpStatusCode statusCode) {
        this.message = message;
        this.statusCode = statusCode.value();
    }
}
