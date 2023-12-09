package com.sparta.stairs.global.dto;

import lombok.Builder;
import org.springframework.http.HttpStatusCode;

public class CustomResponseDto {
    private String message;
    private HttpStatusCode statusCode;

    @Builder
    public CustomResponseDto(String message, HttpStatusCode statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
