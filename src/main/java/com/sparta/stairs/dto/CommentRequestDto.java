package com.sparta.stairs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentRequestDto {

    private Long id;

    @NotBlank
    @Size(max = 40, message = "최대 40자까지 입력")
    private String comment;
}
