package com.sparta.stairs.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
