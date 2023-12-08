package com.sparta.stairs.global;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {


    public CustomException(HttpStatus status, String message) {
        super(message);
    }
}

