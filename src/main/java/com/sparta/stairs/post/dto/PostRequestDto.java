package com.sparta.stairs.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {


    @Schema(description = "title", example = "글 제목")
    @NotBlank
    private String title;

    @Schema(description = "content", example = "글 내용")
    @NotBlank
    private String content;
}

